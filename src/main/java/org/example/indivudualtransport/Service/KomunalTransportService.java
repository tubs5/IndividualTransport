package org.example.indivudualtransport.Service;

import org.example.indivudualtransport.Model.komunalTransport.PublicWalkRoute;
import org.example.indivudualtransport.Model.komunalTransport.RouteRequestDTO;
import org.example.indivudualtransport.Model.komunalTransport.StationResponse;
import org.example.indivudualtransport.Model.route.Coordinates;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Tobias Heidlund
 */
@Service
public class KomunalTransportService {
    
    public StationResponse getClosestStation(Coordinates coordinates, String username){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("username", username);
        HttpEntity<Coordinates> request = new HttpEntity<>(coordinates, headers);
        try {
            ResponseEntity<StationResponse> route  = new RestTemplate().exchange("https://mu23publictransport.azurewebsites.net/api/v1/public-transport/routes/stations", HttpMethod.POST, request, new ParameterizedTypeReference<>() { });
            if (route.getStatusCode().is2xxSuccessful()){
                return route.getBody();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }



    public PublicWalkRoute checkForPublicTransportRoute(String from, String to, String username) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("username", username);
        HttpEntity<RouteRequestDTO> request = new HttpEntity<>(new RouteRequestDTO(from, to), headers);
        try {
            ResponseEntity<PublicWalkRoute> route  = new RestTemplate().exchange("https://mu23publictransport.azurewebsites.net/api/v1/public-transport/routes", HttpMethod.POST, request, new ParameterizedTypeReference<PublicWalkRoute>() { });
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
