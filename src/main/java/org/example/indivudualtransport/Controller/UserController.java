package org.example.indivudualtransport.Controller;

import org.example.indivudualtransport.Model.route.Route;
import org.example.indivudualtransport.Model.route.Routes.BaseRoute;
import org.example.indivudualtransport.Model.route.TypeOfTravel;
import org.example.indivudualtransport.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author Tobias Heidlund
 */
@RestController
@RequestMapping("api/v1/favorite")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("list")
    public ResponseEntity<HashMap<Long,String>> getRoutes(@RequestHeader String username){
        HashMap<Long,String> routes = userService.getRoutesByUsernameShort(username);
         if (routes.isEmpty()){
             return ResponseEntity.noContent().build();
         }
         else {
             return ResponseEntity.ok(routes);
         }
    }
    //TODO:FIX
    @GetMapping("{id}")
    public ResponseEntity<List<BaseRoute>> getRoute(@RequestHeader String username, @PathVariable long id){
        List<BaseRoute> route = userService.getRouteById(id,username);
        if (route == null){
            return ResponseEntity.noContent().build();
        }
        else {
             return ResponseEntity.ok(route);
        }
    }

    //TODO:FIXTYPE OF TRAVEL
    @GetMapping("{from}/{to}/{TypeOfTravel}")
    public ResponseEntity<List<BaseRoute>> getRoute(@RequestHeader String username,@PathVariable TypeOfTravel TypeOfTravel,
        @PathVariable String from, @PathVariable String to){
        List<BaseRoute> routes = userService.getRouteFavoritesFromTo(username,from,to);
        if (routes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.ok(routes);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Route> removeRoute(@RequestHeader String username, @PathVariable long id){
        userService.removeById(id);
        return ResponseEntity.ok().build();
    }


}
