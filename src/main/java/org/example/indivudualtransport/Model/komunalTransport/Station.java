package org.example.indivudualtransport.Model.komunalTransport;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.indivudualtransport.Model.route.Coordinates;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String name;

    @Embedded
    private Coordinates Coords;

    @Enumerated(EnumType.STRING)
    private StationTypes type;
}