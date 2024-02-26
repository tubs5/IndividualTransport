package org.example.indivudualtransport.Service;

import org.example.indivudualtransport.Model.route.ComputedRoute;
import org.example.indivudualtransport.Model.route.Route;
import org.example.indivudualtransport.Model.route.TypeOfTravel;
import org.example.indivudualtransport.Model.UserFavorite;
import org.example.indivudualtransport.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Tobias Heidlund
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RouteService routeService;

    public void addFavorite(Route route, String username){
        if(userRepository.getByUsernameAndRoute(username,route) != null) return;

        UserFavorite userFavorite = new UserFavorite();
        userFavorite.setUsername(username);
        userFavorite.setRoute(route);
        userRepository.save(userFavorite);
    }
    public void removeFavorite(Route route, String username){
        userRepository.deleteByUsernameAndRoute(username,route);
    }

    public List<Route> getRoutesByUsername(String username){
        List<Route> routes = new ArrayList<>();
        for (UserFavorite userFavorite : userRepository.getByUsername(username)) {
            routes.add(userFavorite.getRoute());
        }
        return routes;
    }
    public HashMap<Long,String> getRoutesByUsernameShort(String username){
        HashMap<Long,String> routes = new HashMap<>();
        for (UserFavorite userFavorite : userRepository.getByUsername(username)) {
            routes.put(userFavorite.getId(), userFavorite.getRoute().getStart() +"  -->  "+userFavorite.getRoute().getStop()+" by: "+ userFavorite.getRoute().getTypeOfTravel());
        }
        return routes;
    }
    public ComputedRoute getRouteById(long id){
        return routeService.getRoute(userRepository.getReferenceById(id).getRoute());
    }

    public void removeById(long id) {
        userRepository.deleteById(id);
    }

    public List<ComputedRoute> getRouteFavoritesFromTo(String username, String from, String to, TypeOfTravel typeOfTravel) {
        List<ComputedRoute> computedRoutes = new ArrayList<>();
        List<Route> routes = getRoutesByUsername(username);
        for (Route route : routes) {
            if(route.getStart().equals(from) && route.getStop().equals(to) &&
                    (route.getTypeOfTravel().equals(typeOfTravel) || typeOfTravel.equals(TypeOfTravel.All))){
                computedRoutes.add(routeService.getRoute(route));
            }
        }
        return computedRoutes;
    }
}
