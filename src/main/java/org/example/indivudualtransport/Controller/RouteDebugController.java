package org.example.indivudualtransport.Controller;

import org.example.indivudualtransport.Model.Route;
import org.example.indivudualtransport.Model.TypeOfTravel;
import org.example.indivudualtransport.Service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Tobias Heidlund
 */
@RestController
@RequestMapping("api/v1/route")
public class RouteDebugController {
    @Autowired
    RouteService routeService;

    @GetMapping("possibleRoutes")
    public ResponseEntity<HashMap<String,String>> possibleRoutes(){
        return ResponseEntity.ok(routeService.getPossibleRoutes(TypeOfTravel.Foot));
    }
    @GetMapping("test")
    public ResponseEntity<Route> testRoute(){
        return ResponseEntity.ok(new Route(0, TypeOfTravel.Foot,101D,324D,"Start","Stop",new ArrayList<>()));
    }

    @PostMapping("addRoute")
    public ResponseEntity<String> addRoute(@RequestBody Route route){
        routeService.saveRoute(route);
        return ResponseEntity.status(HttpStatus.OK).build();
    }




}
