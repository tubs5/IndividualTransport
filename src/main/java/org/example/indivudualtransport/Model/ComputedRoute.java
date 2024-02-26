package org.example.indivudualtransport.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * @author Tobias Heidlund
 */
@AllArgsConstructor
@Data
public class ComputedRoute {
    Route route;
    LocalTime timeOfArrival;
    Weather weather;
    List<Route> alternativeRoutes;

}
