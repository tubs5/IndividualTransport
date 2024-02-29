package org.example.indivudualtransport.Model.route;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * @author Tobias Heidlund
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED, force=true)
@Data
@Entity
public class Route {

    public Route(TypeOfTravel typeOfTravel, String startLoc, String endLoc) {
        this.typeOfTravel = typeOfTravel;
        this.start = startLoc;
        this.stop = endLoc;
    }

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



    TypeOfTravel typeOfTravel;
    Double distance;
    Double time;
    String start;
    String stop;
    @ElementCollection
    List<String> waypoints;


}
