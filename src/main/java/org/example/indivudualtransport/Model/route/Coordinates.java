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

    @Basic double lat,lon;
    public String asString(){
        return lat+","+lon;
    }
}
