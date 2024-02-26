package org.example.indivudualtransport.Controller;

import org.example.indivudualtransport.Model.route.ComputedRoute;
import org.example.indivudualtransport.Model.route.TypeOfTravel;
import org.example.indivudualtransport.Service.RouteService;
import org.example.indivudualtransport.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Tobias Heidlund
 */
@RestController
@RequestMapping("api/v1/routes/{modeOfTransport}/{startPos}/{dest}")
public class RouteController {
    @Autowired
    RouteService routeService;
    @Autowired
    UserService userService;
    @GetMapping()
    public ResponseEntity<ComputedRoute> getRoute(@PathVariable String modeOfTransport,
                                                  @PathVariable String startPos, @PathVariable String dest){
        return ResponseEntity.ok(routeService.getRoute(startPos,dest,TypeOfTravel.valueOf(modeOfTransport)));
    }
    @PostMapping("/favorite")
    public ResponseEntity<ComputedRoute> favorite(@RequestHeader String username, @PathVariable String modeOfTransport,
                                           @PathVariable String startPos, @PathVariable String dest){
        ComputedRoute route = routeService.getRoute(startPos,dest,TypeOfTravel.valueOf(modeOfTransport));
        userService.addFavorite(route.getRoute(),username);

        return ResponseEntity.ok(route);
    }
    @DeleteMapping("/favorite")
    public ResponseEntity<ComputedRoute> unFavorite(@RequestHeader String username, @PathVariable String modeOfTransport,
                                           @PathVariable String startPos, @PathVariable String dest){
        ComputedRoute route = routeService.getRoute(startPos,dest,TypeOfTravel.valueOf(modeOfTransport));
        userService.removeFavorite(route.getRoute(),username);

        return ResponseEntity.ok(route);
    }
}
