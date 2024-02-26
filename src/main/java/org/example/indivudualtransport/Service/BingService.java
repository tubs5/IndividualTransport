package org.example.indivudualtransport.Service;

import org.example.indivudualtransport.secret;
import org.example.indivudualtransport.Model.route.Route;
import org.example.indivudualtransport.Model.route.TypeOfTravel;
import org.example.indivudualtransport.Model.bing.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * @author Tobias Heidlund
 */

@Service
public class BingService {

    public ArrayList<Route> getCarPath(String startPos, String dest){
        System.err.println("REQUESTING BING");
        ArrayList<Route> routes = new ArrayList<>();

        RestTemplate resT = new RestTemplate();
        //TODO: MUST BE Driving [default] OR Walking And make into a service
        ResponseEntity<BingMapsResponse> response =
                resT.getForEntity("http://dev.virtualearth.net/REST/v1/Routes?wayPoint.1="+startPos+"&wp.2="+dest+"&maxSolutions=3&travelMode="+"Driving"+"&key="+ secret.bingKey,
                        BingMapsResponse.class);

        for (ResourceSet rs : response.getBody().getResourceSets()){
            RouteLeg r = rs.getResources()[0].getRouteLegs()[0];

            ArrayList<String> route = new ArrayList<String>();
            for (ItineraryItem itineraryItem : r.getItineraryItems()) {
                route.add(itineraryItem.getInstruction().getText());
            }

            routes.add(new Route(0,TypeOfTravel.Car,r.getTravelDistance(),r.getTravelDuration(),
                    startPos,
                    dest,route));
        }
        return routes;
    }

    public ArrayList<Route> getWalkPath(String startPos, String dest){
        System.err.println("REQUESTING BING");
        ArrayList<Route> routes = new ArrayList<>();

        RestTemplate resT = new RestTemplate();
        //TODO: MUST BE Driving [default] OR Walking And make into a service
        ResponseEntity<BingMapsResponse> response =
                resT.getForEntity("http://dev.virtualearth.net/REST/v1/Routes?wayPoint.1="+startPos+"&wp.2="+dest+"&maxSolutions=3&travelMode="+"Walking"+"&key="+ secret.bingKey,
                        BingMapsResponse.class);

        for (ResourceSet rs : response.getBody().getResourceSets()){
            RouteLeg r = rs.getResources()[0].getRouteLegs()[0];

            ArrayList<String> route = new  ArrayList<String>();
            for (ItineraryItem itineraryItem : r.getItineraryItems()) {
                route.add(itineraryItem.getInstruction().getText());
            }

            routes.add(new Route(0,TypeOfTravel.Foot,r.getTravelDistance(),r.getTravelDuration(),
                    startPos,
                    dest,route));
        }
        return routes;
    }

    public ArrayList<Route> getBikePath(String startPos, String dest){
        System.err.println("REQUESTING BING");
        ArrayList<Route> routes = new ArrayList<>();

        RestTemplate resT = new RestTemplate();
        //TODO: MUST BE Driving [default] OR Walking And make into a service
        ResponseEntity<BingMapsResponse> response =
                resT.getForEntity("http://dev.virtualearth.net/REST/v1/Routes?wayPoint.1="+startPos+"&wp.2="+dest+"&maxSolutions=3&travelMode="+"Walking"+"&key="+ secret.bingKey,
                        BingMapsResponse.class);

        for (ResourceSet rs : response.getBody().getResourceSets()){
            RouteLeg r = rs.getResources()[0].getRouteLegs()[0];

            ArrayList<String> route = new  ArrayList<String>();
            for (ItineraryItem itineraryItem : r.getItineraryItems()) {
                route.add(itineraryItem.getInstruction().getText());
            }

            routes.add(new Route(0,TypeOfTravel.Bike,r.getTravelDistance(),r.getTravelDuration()/2.0,
                   startPos,
                    dest,route));
        }
        return routes;
    }
}
