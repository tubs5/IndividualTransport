package org.example.indivudualtransport.Model.route.Routes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.indivudualtransport.Model.route.Route;
import org.example.indivudualtransport.Model.route.Weather;

/**
 * @author Tobias Heidlund
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WalkOrBikeRoute extends BaseRoute{
    Weather weather;


    public WalkOrBikeRoute(Route route, Weather weather) {
        this.weather = weather;
        this.route = route;
    }
}
