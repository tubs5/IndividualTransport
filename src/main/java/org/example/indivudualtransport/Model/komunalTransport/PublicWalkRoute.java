package org.example.indivudualtransport.Model.komunalTransport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.indivudualtransport.Model.route.Route;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicWalkRoute {

    private PublicRoute publicRoute;
    private Route walkingRoute;
}