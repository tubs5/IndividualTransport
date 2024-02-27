package org.example.indivudualtransport.Model.route;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tobias Heidlund
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Coordinates {

    long Id;
    double lat,lon;
    public double getDistance(Coordinates c){
        return Math.sqrt(Math.pow(lat-c.lat,2) + Math.pow(lon-c.lon,2));
    }
    public String asString(){
        return lat+","+lon;
    }
}
