package org.example.indivudualtransport.Model.bing;

import lombok.Data;
import lombok.ToString;

/**
 * @author Tobias Heidlund
 */
@Data
@ToString
public class ItineraryItem {
    String compassDirection;
    Instruction instruction;
    String towardsRoadName;
    double travelDistance;
    double travelDuration;
}
