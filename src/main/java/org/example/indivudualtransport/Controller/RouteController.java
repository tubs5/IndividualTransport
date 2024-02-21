package org.example.indivudualtransport.Controller;

import org.example.indivudualtransport.Model.Route;
import org.example.indivudualtransport.Model.TypeOfTravel;
import org.example.indivudualtransport.Service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author Tobias Heidlund
 */
@RestController
@RequestMapping("api/v1/route")
public class RouteController {
    @Autowired
    RouteService routeService;

    @GetMapping("possibleRoutes")
    public ResponseEntity<HashMap<String,String>> possibleRoutes(){
        return ResponseEntity.ok(routeService.getPossibleRoutes());
    }
    @GetMapping("test")
    public ResponseEntity<Route> testRoute(){
        return ResponseEntity.ok(new Route(0,TypeOfTravel.Foot,101L,"time","Start","Stop","Waypoints"));
    }


    @GetMapping("{modeOfTransport}/{startPos}/{dest}")
    public ResponseEntity<List<Route>> getRoute(@PathVariable String modeOfTransport,
                                                @PathVariable String startPos, @PathVariable String dest){
        return ResponseEntity.ok(routeService.getRoute(TypeOfTravel.valueOf(modeOfTransport),startPos,dest));
    }

    @PostMapping("addRoute")
    public ResponseEntity<String> addRoute(@RequestBody Route route){
        routeService.saveRoute(route);
        return ResponseEntity.status(HttpStatus.OK).build();
    }




}
