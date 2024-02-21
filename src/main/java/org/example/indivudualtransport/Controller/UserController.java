package org.example.indivudualtransport.Controller;

import org.example.indivudualtransport.Model.Route;
import org.example.indivudualtransport.Model.TypeOfTravel;
import org.example.indivudualtransport.Service.RouteService;
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
@RequestMapping("api/v1/routes/favorites")
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
    public ResponseEntity<Route> getRoute(@RequestHeader String username, @PathVariable long id){
        Route route = userService.getRouteById(id);
        if (route == null) return ResponseEntity.noContent().build();
        else return ResponseEntity.ok(route);
    }
    @GetMapping("{id}/{TypeOfTravel}")
    public ResponseEntity<Route> getRoute(@RequestHeader String username, @PathVariable long id,@PathVariable TypeOfTravel typeOfTravel){
        Route route = userService.getRouteById(id);
        if (route == null) return ResponseEntity.noContent().build();
        else return ResponseEntity.ok(route);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Route> removeRoute(@RequestHeader String username, @PathVariable long id){
        userService.removeById(id);
        return ResponseEntity.ok().build();
    }


}
