package org.example.indivudualtransport.Service;

import org.example.indivudualtransport.Model.Route;
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

    public void addFavorite(Route route, String username){
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
            routes.put(userFavorite.getId(), userFavorite.getRoute().getStart() +"->"+userFavorite.getRoute().getStop());
        }
        return routes;
    }
    public Route getRouteById(long id){
        return userRepository.getReferenceById(id).getRoute();
    }

    public void removeById(long id) {
        userRepository.deleteById(id);
    }
}
