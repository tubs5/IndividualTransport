package org.example.indivudualtransport.Model.route.Routes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.indivudualtransport.Model.route.Route;

import java.time.LocalTime;
import java.util.HashMap;

/**
 * @author Tobias Heidlund
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarRoute extends BaseRoute {
    HashMap<String ,Long> estimatedDelays;

    public CarRoute(Route route, LocalTime timoOfArrival, HashMap<String, Long> delays) {
        this.route = route;
        this.timeOfArrival = timoOfArrival;
        this.estimatedDelays = delays;

    }

    void addDelay(String reason, long time){
        estimatedDelays.put(reason,time);
        timeOfArrival = timeOfArrival.plusSeconds(time);
    }
}
