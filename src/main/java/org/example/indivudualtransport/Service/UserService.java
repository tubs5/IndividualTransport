package org.example.indivudualtransport.Service;

import org.example.indivudualtransport.Model.route.Route;
import org.example.indivudualtransport.Model.route.Routes.BaseRoute;
import org.example.indivudualtransport.Model.route.Routes.CarRoute;
import org.example.indivudualtransport.Model.route.Routes.CollectiveRoute;
import org.example.indivudualtransport.Model.route.Routes.WalkOrBikeRoute;
import org.example.indivudualtransport.Model.UserFavorite;
import org.example.indivudualtransport.Model.route.TypeOfTravel;
import org.example.indivudualtransport.Repository.UserRepository;
import org.jetbrains.annotations.NotNull;
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
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RouteService routeService;
    @Autowired
    WeatherService weatherService;


    public void removeFavorite(String baseRoute, String baseRoute1, TypeOfTravel modeOfTransport, String username) {
        userRepository.deleteByUsernameAndFromAndToAndTypeOfTravel(username,baseRoute,baseRoute1,modeOfTransport);
    }

    public List<List<BaseRoute>> getRoutesByUsername(String username){
        List<List<BaseRoute>> listRoutes = new ArrayList<>();
        for (UserFavorite userFavorite : userRepository.getByUsername(username)) {
            List<BaseRoute> routes = getRoutes(username, userFavorite);
            listRoutes.add(routes);
        }
        return listRoutes;
    }

    @NotNull
    private List<BaseRoute> getRoutes(String username, UserFavorite userFavorite) {
        List<BaseRoute> routes = new ArrayList<>();
        for (Route route : userFavorite.getRoute()) {
            switch (route.getTypeOfTravel()){
                case Bike,Foot -> routes.add(new WalkOrBikeRoute(route,
                        weatherService.getWeather(route.getStop(),getEstimatedArrivalTime(route))));
                case Car -> routes.add(new CarRoute(route,getEstimatedArrivalTime(route),routeService.getDelayForRoute(route)));
                case Mixed -> routes.add(new CollectiveRoute(routeService.komunalTransportService.checkForPublicTransportRoute(route.getStart(),route.getStop(), username).getPublicRoute()));
            }
        }
        return routes;
    }

    public HashMap<Long,String> getRoutesByUsernameShort(String username){
        HashMap<Long,String> routes = new HashMap<>();
        for (UserFavorite userFavorite : userRepository.getByUsername(username)) {
            routes.put(userFavorite.getId(), userFavorite.getRoute().get(0).getStart() +"  -->  "+
                    userFavorite.getRoute().get(userFavorite.getRoute().size()-1).getStop()+" by: "+
                    userFavorite.getTypeOfTravel());
        }
        return routes;
    }
    public List<BaseRoute> getRouteById(long id,String username){
        UserFavorite favorite = userRepository.getReferenceById(id);
        return getRoutes(username,favorite);

    }

    public void removeById(long id) {
        userRepository.deleteById(id);
    }

    public List<BaseRoute> getRouteFavoritesFromTo(String username, String from, String to) {
        List<List<BaseRoute>> routes = getRoutesByUsername(username);
        for (List<BaseRoute> route : routes) {
            if(route.get(0).getRoute().getStart().equals(from) &&
                    route.get(route.size()-1).getRoute().getStop().equals(to)){
                return route;
            }
        }
        return null;
    }

    public void addFavorite(List<BaseRoute> baseRoutes, String username) {
        List<Route> routes = new ArrayList<>();
        TypeOfTravel typeOfTravel = baseRoutes.get(0).getRoute().getTypeOfTravel();
        for (BaseRoute baseRoute : baseRoutes) {
            routes.add(baseRoute.getRoute());
            if (baseRoute.getRoute().getTypeOfTravel() == TypeOfTravel.Mixed){
                typeOfTravel = TypeOfTravel.Mixed;
            }
        }
        if(userRepository.getByUsernameAndFromAndToAndTypeOfTravel(username,baseRoutes.get(0).getRoute().getStart(),
                baseRoutes.get(baseRoutes.size()-1).getRoute().getStop(),typeOfTravel) != null) return;
        UserFavorite userFavorite = new UserFavorite(username,
                baseRoutes.get(0).getRoute().getStart(),
                baseRoutes.get(baseRoutes.size()-1).getRoute().getStop(),
                    typeOfTravel,routes
                );
        userRepository.save(userFavorite);
    }
    private LocalTime getEstimatedArrivalTime(Route route) {
        return LocalTime.now().plusSeconds(route.getTime().longValue());
    }



}
