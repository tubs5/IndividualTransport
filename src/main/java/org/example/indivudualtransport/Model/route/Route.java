package org.example.indivudualtransport.Model.route;

import jakarta.persistence.*;
import lombok.*;

import java.awt.*;
import java.util.List;

/**
 * @author Tobias Heidlund
 */
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED, force=true)
@Data
@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @AttributeOverrides({
            @AttributeOverride(name="lat",column=@Column(name="startlat")),
            @AttributeOverride(name="lon",column=@Column(name="startlon"))
    })
    @Embedded
    Coordinates startCoords;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="lat",column=@Column(name="stoplat")),
            @AttributeOverride(name="lon",column=@Column(name="stoplon"))
    })
    Coordinates stopCoords;
    final TypeOfTravel typeOfTravel;
    final Double distance;
    final Double time;
    final String start;
    final String stop;
    @ElementCollection
    List<String> waypoints;


}
