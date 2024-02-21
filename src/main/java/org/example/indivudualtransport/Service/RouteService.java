package org.example.indivudualtransport.Service;

import org.example.indivudualtransport.Model.Route;
import org.example.indivudualtransport.Model.TypeOfTravel;
import org.example.indivudualtransport.Repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author Tobias Heidlund
 */
@Service
public class RouteService {
    @Autowired
    RouteRepository routeRepository;

    public void saveRoute(Route route){
        routeRepository.save(route);
    }
    public List<Route> getRoute(TypeOfTravel typeOfTravel, String from, String to){
        return routeRepository.getRoutesByStartAndStopAndTypeOfTravel(from,to,typeOfTravel);
    }


    public HashMap<String, String> getPossibleRoutes() {
        List<Route> routes = routeRepository.getRoutesByTypeOfTravel(TypeOfTravel.Foot);
        HashMap<String, String> startStop = new HashMap<>();
        for (Route route : routes) {
            startStop.put(route.getStart(),route.getStop());
        }
        return startStop;
    }
}
