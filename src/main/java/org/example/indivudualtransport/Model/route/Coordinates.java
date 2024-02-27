package org.example.indivudualtransport.Model.route;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tobias Heidlund
 */
@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Coordinates {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    double lat,lon;
    public double getDistance(Coordinates c){
        return Math.sqrt(Math.pow(lat-c.lat,2) + Math.pow(lon-c.lon,2));
    }
    public String asString(){
        return lat+","+lon;
    }
}
