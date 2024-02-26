package org.example.indivudualtransport.Repository;

import org.example.indivudualtransport.Model.route.Route;
import org.example.indivudualtransport.Model.route.TypeOfTravel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Tobias Heidlund
 */
@Repository
public interface RouteRepository extends JpaRepository<Route,Long> {
    List<Route> getRoutesByStartAndStopAndTypeOfTravel(String start, String stop, TypeOfTravel typeOfTravel);
    List<Route> getRoutesByTypeOfTravel(TypeOfTravel typeOfTravel);

}
