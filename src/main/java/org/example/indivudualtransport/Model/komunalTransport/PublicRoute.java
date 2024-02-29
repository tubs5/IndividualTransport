package org.example.indivudualtransport.Model.komunalTransport;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PublicRoute {
    private long Id;

    private String startLoc;

    private String endLoc;

    private String departTime;

    private String arrival;

    private int numberOfSwaps;

    private double travelTime;

    private Boolean favorite;
}