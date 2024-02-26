package org.example.indivudualtransport.Service;

import org.example.indivudualtransport.Model.route.ComputedRoute;
import org.example.indivudualtransport.Model.route.Route;
import org.example.indivudualtransport.Model.route.TypeOfTravel;
import org.example.indivudualtransport.Repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
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

    public void saveRoute(Route route){
        routeRepository.save(route);
    }
    public void saveRoutes(List<Route> routes){
        routeRepository.saveAll(routes);
    }
    private List<Route> getRoute(TypeOfTravel typeOfTravel, String from, String to){
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

    public ComputedRoute getRoute(String from, String to,TypeOfTravel typeOfTravel){
        List<Route> routes = getRoute(typeOfTravel,from,to);
        ComputedRoute computedRoute = getRoute(routes.get(0));
        routes.remove(0);
        computedRoute.setAlternativeRoutes(routes);
        return computedRoute;
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
