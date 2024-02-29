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
    List<Route> route;
    LocalTime timeOfArrival;
    Weather weather;
    HashMap<String ,Long> estimatedDelays;
    List<ComputedRoute> alternativeRoutes;
    PublicWalkRoute alternativePublicRoute;
    public ComputedRoute(Route route, LocalTime timeOfArrival, Weather weather, List<ComputedRoute> alternativeRoutes) {
        this.route = new ArrayList<>();
        this.route.add(route);
        this.timeOfArrival = timeOfArrival;
        this.weather = weather;
        this.alternativeRoutes = alternativeRoutes;
    }

}
