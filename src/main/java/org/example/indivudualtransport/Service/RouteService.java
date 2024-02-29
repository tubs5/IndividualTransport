package org.example.indivudualtransport.Service;

import org.example.indivudualtransport.Model.komunalTransport.PublicWalkRoute;
import org.example.indivudualtransport.Model.komunalTransport.StationResponse;
import org.example.indivudualtransport.Model.route.ComputedRoute;
import org.example.indivudualtransport.Model.route.Route;
import org.example.indivudualtransport.Model.route.Routes.BaseRoute;
import org.example.indivudualtransport.Model.route.Routes.CarRoute;
import org.example.indivudualtransport.Model.route.Routes.CollectiveRoute;
import org.example.indivudualtransport.Model.route.Routes.WalkOrBikeRoute;
import org.example.indivudualtransport.Model.route.TypeOfTravel;
import org.example.indivudualtransport.Repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.*;

/**
 * @author Tobias Heidlund
 */
@Service
public class RouteService {
    @Autowired
    RouteRepository routeRepository;
    @Autowired
    BingService bingService;
    @Autowired
    WeatherService weatherService;
    @Autowired
    KomunalTransportService komunalTransportService;

    public void saveRoute(Route route){
        routeRepository.save(route);
    }
    public void saveRoutes(List<Route> routes){
        routeRepository.saveAll(routes);
    }
    public List<Route> getRoute(TypeOfTravel typeOfTravel, String from, String to){
        List<Route> routes = routeRepository.getRoutesByStartAndStopAndTypeOfTravel(from,to,typeOfTravel);
        if(routes.isEmpty()){
            List<Route> bingRoutes;
            switch (typeOfTravel){
                case Car ->  bingRoutes= bingService.getCarPath(from,to);
                case Bike -> bingRoutes = bingService.getBikePath(from,to);
                default ->   bingRoutes = bingService.getWalkPath(from,to);
            }
            saveRoutes(bingRoutes);
            routes.addAll(bingRoutes);
        }
        return routes;
    }

    public List<List<BaseRoute>> getRoute(String from, String to,TypeOfTravel typeOfTravel, String username){
        List<List<BaseRoute>> listOfComputedRoutes = new ArrayList<>();
        List<Route> routes = getRoute(typeOfTravel,from,to);
        switch (typeOfTravel){
            case Car -> {
                for (Route route : routes) {
                    List<BaseRoute> computedRoutes = new ArrayList<>();
                    HashMap<String, Long> delays = getDelayForRoute(route);
                    LocalTime timoOfArrival = getEstimatedArrivalTime(route);
                    for (Long value : delays.values()) {
                        timoOfArrival.plusSeconds(value);
                    }
                    computedRoutes.add(new CarRoute(route,timoOfArrival,delays));
                    listOfComputedRoutes.add(computedRoutes);
                }

            }
            case Mixed -> {
                List<BaseRoute> mixedRoute = generateMixedRoute(from, to, username, routes.get(0));
                if(mixedRoute == null){
                    mixedRoute = new ArrayList<>();
                    mixedRoute.add(new WalkOrBikeRoute(routes.get(0),weatherService.getWeather(to,getEstimatedArrivalTime(routes.get(0)))));
                }
                listOfComputedRoutes.add(mixedRoute);
            }
            case Foot, Bike -> {
                List<BaseRoute> computedRoutes = new ArrayList<>();
                for (Route route : routes) {
                    LocalTime timoOfArrival = getEstimatedArrivalTime(route);
                    computedRoutes.add(new WalkOrBikeRoute(route,weatherService.getWeather(route.getStop(),timoOfArrival)));
                }
                listOfComputedRoutes.add(computedRoutes);

                PublicWalkRoute pubtrans = komunalTransportService.checkForPublicTransportRoute(from, to, username);
                if (pubtrans != null) {
                    if (pubtrans.getWalkingRoute().getId() != computedRoutes.get(0).getRoute().getId()) {
                        List<BaseRoute> alternateRoute = new ArrayList<>();
                        LocalTime timoOfArrival = getEstimatedArrivalTime(pubtrans.getWalkingRoute());
                        alternateRoute.add(new WalkOrBikeRoute(pubtrans.getWalkingRoute(),
                                weatherService.getWeather(pubtrans.getWalkingRoute().getStop(), timoOfArrival)));
                        alternateRoute.add(new CollectiveRoute(pubtrans.getPublicRoute()));
                        listOfComputedRoutes.add(alternateRoute);
                    }
                }

            }
        }
        return listOfComputedRoutes;
    }

    private List<BaseRoute> generateMixedRoute(String from, String to, String username,Route altWalkRoute) {
        List<BaseRoute> routes = new ArrayList<>();
        StationResponse closestStartStation = komunalTransportService.getClosestStation(altWalkRoute.getStartCoords(), username);
        StationResponse closestStopStation = komunalTransportService.getClosestStation(altWalkRoute.getStopCoords(), username);
        PublicWalkRoute publicRoute = komunalTransportService.checkForPublicTransportRoute(closestStartStation.getStation().getName(),
                closestStopStation.getStation().getName(), username);
        if(publicRoute.getPublicRoute() == null){
            System.err.println("No Public route could be found");
            return null;
        }else{
            Route firstLeg = getRoute(TypeOfTravel.Foot,from,
                    closestStartStation.getStation().getCoords().asString()).get(0);
            routes.add(new WalkOrBikeRoute(firstLeg,
                    weatherService.getWeather(to,getEstimatedArrivalTime(firstLeg))));
            CollectiveRoute pubTrans = new CollectiveRoute(publicRoute.getPublicRoute());
            routes.add(pubTrans);
            routeRepository.save(pubTrans.getRoute());
            Route finalLeg = getRoute(TypeOfTravel.Foot,
                    closestStopStation.getStation().getCoords().asString(),to).get(0);
            routes.add(new WalkOrBikeRoute(finalLeg,
                    weatherService.getWeather(to,getEstimatedArrivalTime(finalLeg))));
        }

        return routes;
    }


    //TODO: HÄMTA riktig förseningsdata
    public HashMap<String,Long> getDelayForRoute(Route route) {
        String[] anledningar = {"Krock","Vägarbete","Köer"};
        HashMap<String, Long> delays = new HashMap<>();
        Random random = new Random();
        for (int i = 0; i < random.nextInt(0, 10); i++) {
            delays.put(anledningar[random.nextInt(0,2)],random.nextLong(200,2000));
        }
        return delays;
    }

    private LocalTime getEstimatedArrivalTime(Route route) {
        return LocalTime.now().plusSeconds(route.getTime().longValue());
    }

    public ComputedRoute getRoute(Route route){
        LocalTime arrivalTime = LocalTime.now().plusSeconds(route.getTime().longValue());
        return new ComputedRoute(route,arrivalTime, weatherService.getWeather(route.getStop(),arrivalTime),null);
    }


    public HashMap<String, String> getPossibleRoutes(TypeOfTravel typeOfTravel) {
        List<Route> routes = routeRepository.getRoutesByTypeOfTravel(typeOfTravel);
        HashMap<String, String> startStop = new HashMap<>();
        for (Route route : routes) {
            startStop.put(route.getStart(),route.getStop());
        }
        return startStop;
    }
}
