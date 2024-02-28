package org.example.indivudualtransport.Service;

import org.example.indivudualtransport.Model.komunalTransport.PublicRoute;
import org.example.indivudualtransport.Model.komunalTransport.PublicWalkRoute;
import org.example.indivudualtransport.Model.komunalTransport.RouteRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Tobias Heidlund
 */
@Service
public class KomunalTransportService {


    public PublicWalkRoute getRoute(String from, String to){
        RestTemplate resT = new RestTemplate();
        return resT.postForEntity("https://mu23publictransport.azurewebsites.net/api/v1/public-transport/routes", new RouteRequestDTO(from,to),PublicWalkRoute.class).getBody();
    }


    public PublicWalkRoute checkForPublicTransportRoute(String from, String to) {
        RestTemplate resT = new RestTemplate();
        ResponseEntity<PublicWalkRoute> route;
        try {
            route = resT.postForEntity("https://mu23publictransport.azurewebsites.net/api/v1/public-transport/routes", new RouteRequestDTO(from,to),PublicWalkRoute.class);
            if (route.getStatusCode().is2xxSuccessful()){
                if(route.getBody().getPublicRoute() != null){
                    return route.getBody();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }
}
