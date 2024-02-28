package org.example.indivudualtransport.Service;

import org.example.indivudualtransport.Model.komunalTransport.PublicRoute;
import org.example.indivudualtransport.Model.komunalTransport.PublicWalkRoute;
import org.example.indivudualtransport.Model.komunalTransport.StationResponse;
import org.example.indivudualtransport.Model.route.ComputedRoute;
import org.example.indivudualtransport.Model.route.Route;
import org.example.indivudualtransport.Model.route.TypeOfTravel;
import org.example.indivudualtransport.Repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                case Car ->bingRoutes= bingService.getCarPath(from,to);
                case Bike -> bingRoutes = bingService.getBikePath(from,to);
                default ->  bingRoutes = bingService.getWalkPath(from,to);
            }
            saveRoutes(bingRoutes);
            routes.addAll(bingRoutes);
        }
        return routes;
    }

    public ComputedRoute getRoute(String from, String to,TypeOfTravel typeOfTravel, String username){
        List<Route> routes = getRoute(typeOfTravel,from,to);
        ComputedRoute computedRoute = getRoute(routes.get(0));
        for (int i = 1; i < routes.size(); i++) {
            addAlternateRoute(routes, routes.get(i), computedRoute);
        }

        if(typeOfTravel == TypeOfTravel.Foot || typeOfTravel == TypeOfTravel.Bike){

            computedRoute.setAlternativePublicRoute(komunalTransportService.checkForPublicTransportRoute(from,to, username));
        }else if (typeOfTravel == TypeOfTravel.Mixed){
            StationResponse closestStartStation = komunalTransportService.getClosestStation(routes.get(0).getStartCoords(), username);
            StationResponse closestStopStation = komunalTransportService.getClosestStation(routes.get(0).getStopCoords(), username);
            PublicWalkRoute publicRoute = komunalTransportService.checkForPublicTransportRoute(closestStartStation.getStation().getName(),
                    closestStopStation.getStation().getName(), username);
            if(publicRoute.getPublicRoute() == null){
                routes.add(routes.get(0));
            }{
                List<Route> mixedRoute = new ArrayList<>();
                mixedRoute.add(getRoute(TypeOfTravel.Foot,from,closestStartStation.getStation().getCoords().asString()).get(0));
                mixedRoute.add(publicRoute.getPublicRoute());
                mixedRoute.add(getRoute(TypeOfTravel.Foot,closestStopStation.getStation().getCoords().asString(),to).get(0));
                addAlternateRoute(routes,routes.get(0),null);
            }
        }
        routes.remove(0);


        return computedRoute;
    }

    private void addAlternateRoute(List<Route> routes, Route i, ComputedRoute computedRoute) {
        LocalTime arrivalTime = LocalTime.now().plusSeconds(i.getTime().longValue());
        computedRoute.addAlternativeRoute( new ComputedRoute(i,arrivalTime,
                weatherService.getWeather(i.getStop(),arrivalTime),null));
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
