package org.example.indivudualtransport.Controller;

import org.example.indivudualtransport.Model.bing.BingMapsResponse;
import org.example.indivudualtransport.Model.Route;
import org.example.indivudualtransport.Model.TypeOfTravel;
import org.example.indivudualtransport.Model.bing.ItineraryItem;
import org.example.indivudualtransport.Model.bing.Resource;
import org.example.indivudualtransport.Service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Tobias Heidlund
 */
@RestController
@RequestMapping("api/v1/routes/{modeOfTransport}/{startPos}/{dest}")
public class RouteController {
    @Autowired
    RouteService routeService;

    @GetMapping()
    public ResponseEntity<List<Route>> getRoute(@PathVariable String modeOfTransport,
                                                @PathVariable String startPos, @PathVariable String dest){
        return ResponseEntity.ok(routeService.getRoute(TypeOfTravel.valueOf(modeOfTransport),startPos,dest));
    }
    @PostMapping("/favorite")
    public ResponseEntity<String> favorite(@RequestHeader String username, @PathVariable String modeOfTransport,
                                           @PathVariable String startPos, @PathVariable String dest){


        return ResponseEntity.ok(null);
    }
    @DeleteMapping("/favorite")
    public ResponseEntity<String> unFavorite(@RequestHeader String username, @PathVariable String modeOfTransport,
                                           @PathVariable String startPos, @PathVariable String dest){
        return ResponseEntity.ok(null);
    }

    @GetMapping("bing")
    public ResponseEntity<List<Route>> getRouteBing(@PathVariable String modeOfTransport,
                                                @PathVariable String startPos, @PathVariable String dest){
        RestTemplate resT = new RestTemplate();

//TODO: MUST BE Driving [default] OR Walking And make into a service
        ResponseEntity<BingMapsResponse> response =
                resT.getForEntity("http://dev.virtualearth.net/REST/v1/Routes?wayPoint.1="+startPos+"&wp.2="+dest+"&maxSolutions=3&travelMode="+modeOfTransport+"&key="+secret.bingKey,
                        BingMapsResponse.class);

        for (ItineraryItem itineraryItem : response.getBody().getResourceSets()[0].getResources()[0].getRouteLegs()[0].getItineraryItems()) {
            System.out.println(itineraryItem.getInstruction().getText());
        }



        return ResponseEntity.ok(routeService.getRoute(TypeOfTravel.valueOf(modeOfTransport),startPos,dest));
    }

}
