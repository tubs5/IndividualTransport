package org.example.indivudualtransport.Model.bing;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RouteLeg{
    Object[] alternateVias;
    double travelDistance, travelDuration;
    String description, endTime;
    RouteSubLeg[] routeSubLegs;
    ItineraryItem[] itineraryItems;
    Point actualStart, actualEnd;
}
