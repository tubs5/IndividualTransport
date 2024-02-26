package org.example.indivudualtransport.Controller;

import org.example.indivudualtransport.Model.route.ComputedRoute;
import org.example.indivudualtransport.Model.route.Route;
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
         if (routes.isEmpty()) return ResponseEntity.noContent().build();
         else return ResponseEntity.ok(routes);
    }
    @GetMapping("{id}")
    public ResponseEntity<ComputedRoute> getRoute(@RequestHeader String username, @PathVariable long id){
        ComputedRoute route = userService.getRouteById(id);
        if (route == null) return ResponseEntity.noContent().build();
        else return ResponseEntity.ok(route);
    }
    @GetMapping("{from}/{to}/{TypeOfTravel}")
    public ResponseEntity<List<ComputedRoute>> getRoute(@RequestHeader String username,@PathVariable TypeOfTravel TypeOfTravel,
        @PathVariable String from, @PathVariable String to){
        List<ComputedRoute> routes = userService.getRouteFavoritesFromTo(username,from,to,TypeOfTravel);
        if (routes.isEmpty()) return ResponseEntity.noContent().build();
        else return ResponseEntity.ok(routes);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Route> removeRoute(@RequestHeader String username, @PathVariable long id){
        userService.removeById(id);
        return ResponseEntity.ok().build();
    }


}
