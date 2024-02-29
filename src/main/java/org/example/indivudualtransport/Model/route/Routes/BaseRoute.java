package org.example.indivudualtransport.Model.route.Routes;

import lombok.Data;
import org.example.indivudualtransport.Model.route.Route;

import java.time.LocalTime;

/**
 * @author Tobias Heidlund
 */
@Data
public abstract class BaseRoute {
    Route route;
    LocalTime timeOfArrival;
}
