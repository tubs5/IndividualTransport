package org.example.indivudualtransport.Model.route.Routes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.indivudualtransport.Model.komunalTransport.PublicRoute;
import org.example.indivudualtransport.Model.route.Route;
import org.example.indivudualtransport.Model.route.TypeOfTravel;

import java.time.LocalTime;

/**
 * @author Tobias Heidlund
 */
@Data
@NoArgsConstructor
public class CollectiveRoute extends BaseRoute {
    PublicRoute publicRoute;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public Route getRoute() {
        return super.getRoute();
    }
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public LocalTime getTimeOfArrival() {
        return super.getTimeOfArrival();
    }

    public CollectiveRoute(PublicRoute publicRoute) {
        this.publicRoute = publicRoute;
        this.route =  new Route(TypeOfTravel.Mixed,publicRoute.getStartLoc(),publicRoute.getEndLoc());

    }
}
