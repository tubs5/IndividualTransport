package org.example.indivudualtransport.Model.bing;

import lombok.Data;
import lombok.ToString;

/**
 * @author Tobias Heidlund
 */
@Data
@ToString
public class BingMapsResponse{
    int statusCode;
    ResourceSet[] resourceSets;

}

