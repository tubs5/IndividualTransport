package org.example.indivudualtransport.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @author Tobias Heidlund
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    TypeOfTravel typeOfTravel;
    Long distance;
    String time;
    String start;
    String stop;
    @Column(length = 61000)
    String waypoints;


}
