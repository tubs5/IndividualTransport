package org.example.indivudualtransport.Service;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import org.example.indivudualtransport.Model.route.Route;
import org.example.indivudualtransport.Model.route.TypeOfTravel;
import org.example.indivudualtransport.Model.bing.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URL;
import java.security.PublicKey;
import java.util.ArrayList;

/**
 * @author Tobias Heidlund
 */

@Service
public class BingService {

    String key;

    private String getKey(){
        if(key != null){
            return key;
        }
        String keyVaultName = "mu23vault";
        String keyVaultUri = "https://" + keyVaultName + ".vault.azure.net";


        SecretClient secretClient = new SecretClientBuilder()
                .vaultUrl(keyVaultUri)
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();
        KeyVaultSecret retrievedSecret = secretClient.getSecret("maps");
        key = retrievedSecret.getValue();
        return key;

    }


    public ArrayList<Route> getCarPath(String startPos, String dest){
        System.err.println("REQUESTING BING");
        ArrayList<Route> routes = new ArrayList<>();

        BingMapsResponse response = getBingMapsResponseResponseEntity(startPos, dest, null);
        extractRoute(response, routes, TypeOfTravel.Car, startPos, dest);

        Point center = GenerateAlternativeRoute(response);
        response = getBingMapsResponseResponseEntity(startPos, dest, center.toString());
        extractRoute(response, routes, TypeOfTravel.Car, startPos, dest);

        return routes;
    }

    @NotNull
    private static Point GenerateAlternativeRoute(BingMapsResponse response) {
        RouteLeg leg = response.getResourceSets()[0].getResources()[0].getRouteLegs()[0];
        Point center = leg.getActualStart().calcCenter(leg.getActualEnd());
        Point subtract = leg.getActualStart().calcCenter(leg.getActualEnd());
        System.out.println(center);
        if(subtract.getCoordinates()[0]>subtract.getCoordinates()[1]){
            center.getCoordinates()[1] += 0.02d;
        }else {
            center.getCoordinates()[0] += 0.02d;
        }
        return center;
    }

    @NotNull
    private BingMapsResponse getBingMapsResponseResponseEntity(String startPos, String dest, String via) {
        StringBuilder url = generateUrl(startPos, dest, via);

        RestTemplate resT = new RestTemplate();
        System.out.println(url.toString());
        ResponseEntity<BingMapsResponse> response =
                resT.getForEntity(url.toString(),
                        BingMapsResponse.class);
        return response.getBody();
    }

    @NotNull
    private StringBuilder generateUrl(String startPos, String dest, String via) {
        int wpIndex = 1;
        StringBuilder url = new StringBuilder("http://dev.virtualearth.net/REST/v1/Routes?wayPoint."+ wpIndex+++"=" + startPos);
        if(via != null){
            url.append("&vwp.").append(wpIndex++).append("=").append(via);
        }
        url.append("&wp.").append(wpIndex++).append("=").append(dest).append("&maxSolutions=3&travelMode=Driving")
                .append("&key=").append(getKey());
        return url;
    }

    private static void extractRoute(BingMapsResponse response, ArrayList<Route> routes, TypeOfTravel car, String startPos, String dest) {
        for (ResourceSet rs : response.getResourceSets()) {
            RouteLeg r = rs.getResources()[0].getRouteLegs()[0];

            ArrayList<String> route = new ArrayList<String>();
            for (ItineraryItem itineraryItem : r.getItineraryItems()) {
                route.add(itineraryItem.getInstruction().getText());
            }

            routes.add(new Route(0, car, r.getTravelDistance(), r.getTravelDuration(),
                    startPos,
                    dest, route));
        }
    }

    public ArrayList<Route> getWalkPath(String startPos, String dest){
        System.err.println("REQUESTING BING");
        ArrayList<Route> routes = new ArrayList<>();

        RestTemplate resT = new RestTemplate();
        //TODO: MUST BE Driving [default] OR Walking And make into a service
        BingMapsResponse response =
                resT.getForEntity("http://dev.virtualearth.net/REST/v1/Routes?wayPoint.1="+startPos+"&wp.2="+dest+"&maxSolutions=3&travelMode="+"Walking"+"&key="+getKey(),
                        BingMapsResponse.class).getBody();

        extractRoute(response, routes, TypeOfTravel.Foot, startPos, dest);
        return routes;
    }

    public ArrayList<Route> getBikePath(String startPos, String dest){
        System.err.println("REQUESTING BING");
        ArrayList<Route> routes = new ArrayList<>();

        RestTemplate resT = new RestTemplate();
        //TODO: MUST BE Driving [default] OR Walking And make into a service
        ResponseEntity<BingMapsResponse> response =
                resT.getForEntity("http://dev.virtualearth.net/REST/v1/Routes?wayPoint.1="+startPos+"&wp.2="+dest+"&maxSolutions=3&travelMode="+"Walking"+"&key="+ getKey(),
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
