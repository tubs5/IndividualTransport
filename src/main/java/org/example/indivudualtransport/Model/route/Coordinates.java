package org.example.indivudualtransport.Model.route;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tobias Heidlund
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coordinates {
    double lat,lon;
    public double getDistance(Coordinates c){
        return Math.sqrt(Math.pow(lat-c.lat,2) + Math.pow(lon-c.lon,2));
    }


}
