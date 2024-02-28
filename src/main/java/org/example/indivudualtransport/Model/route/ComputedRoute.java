package org.example.indivudualtransport.Model.route;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.indivudualtransport.Model.komunalTransport.PublicWalkRoute;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Tobias Heidlund
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ComputedRoute {
    Route route;
    LocalTime timeOfArrival;
    Weather weather;
    HashMap<String ,Long> estimatedDelays;
    List<ComputedRoute> alternativeRoutes;
    PublicWalkRoute alternativePublicRoute;
    public ComputedRoute(Route route, LocalTime timeOfArrival, Weather weather, List<ComputedRoute> alternativeRoutes) {
        this.route = route;
        this.timeOfArrival = timeOfArrival;
        this.weather = weather;
        this.alternativeRoutes = alternativeRoutes;
    }
    void addDelay(String reason, long time){
        estimatedDelays.put(reason,time);
        timeOfArrival = timeOfArrival.plusSeconds(time);
    }



    public void addAlternativeRoute(ComputedRoute route){
        if(alternativeRoutes == null){
            alternativeRoutes = new ArrayList<>();
        }
        alternativeRoutes.add(route);
    }

}
