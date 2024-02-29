package org.example.indivudualtransport.Controller;

import org.example.indivudualtransport.Config;
import org.example.indivudualtransport.Model.komunalTransport.PublicWalkRoute;
import org.example.indivudualtransport.Model.komunalTransport.RouteRequestDTO;
import org.example.indivudualtransport.Model.route.Route;
import org.example.indivudualtransport.Model.route.TypeOfTravel;
import org.example.indivudualtransport.Service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * @author Tobias Heidlund
 */
@RestController
@RequestMapping("api/v1/route")
public class RouteDebugController {
    @Autowired
    RouteService routeService;
    @Autowired
    private Config properties;

    @GetMapping("possibleRoutes")
    public ResponseEntity<HashMap<String,String>> possibleRoutes(){
        return ResponseEntity.ok(routeService.getPossibleRoutes(TypeOfTravel.Foot));
    }
    @GetMapping("test")
    public ResponseEntity<String> testRoute(){
        return ResponseEntity.ok("HELLOTEST"+properties.getMessage());
        //return ResponseEntity.ok(new Route(0, TypeOfTravel.Foot,101D,324D,"Start","Stop",new ArrayList<>()));
    }

    @PostMapping("addRoute")
    public ResponseEntity<String> addRoute(@RequestBody Route route){
        routeService.saveRoute(route);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PostMapping("TEST123")
    public ResponseEntity<PublicWalkRoute> getPublicWalkRoute(@RequestBody RouteRequestDTO requestDTO) {

        return ResponseEntity.ok(new PublicWalkRoute(null,null));
    }

    @GetMapping("TEST123")
    public ResponseEntity<String> getPublicWalkRoute() {

        RestTemplate temp = new RestTemplate();
        ResponseEntity<PublicWalkRoute> string = temp.postForEntity("http://localhost:8080/api/v1/route/TEST123",new RouteRequestDTO("apa123","stopPos"), PublicWalkRoute.class);
        return ResponseEntity.ok(string.toString());
    }

}
