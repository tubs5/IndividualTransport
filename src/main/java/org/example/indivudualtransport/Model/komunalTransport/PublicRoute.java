package org.example.indivudualtransport.Model.komunalTransport;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.indivudualtransport.Model.route.Route;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PublicRoute extends Route {
    private long Id;

    private String startLoc;

    private String endLoc;

    private String departTime;

    private String arrival;

    private int numberOfSwaps;

    private double travelTime;

    private Boolean favorite;
}