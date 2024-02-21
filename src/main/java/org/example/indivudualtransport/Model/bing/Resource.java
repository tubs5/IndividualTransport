package org.example.indivudualtransport.Model.bing;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Resource{
    String id, distanceUnit,durationUnit;
    RouteLeg[] routeLegs;
    RoutePath routePath;
}
