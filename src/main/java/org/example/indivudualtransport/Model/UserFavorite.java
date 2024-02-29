package org.example.indivudualtransport.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.indivudualtransport.Model.route.Route;
import org.example.indivudualtransport.Model.route.TypeOfTravel;

import java.util.List;

/**
 * @author Tobias Heidlund
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Table(uniqueConstraints = {@UniqueConstraint(name = "uniqueUsernameRoute",columnNames = {"username","from","to","typeOfTravel"})})
public class UserFavorite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String username;
    @Column(name="origin")
    String from;
    @Column(name="destination")
    String to;
    TypeOfTravel typeOfTravel;
    @ManyToMany
    List<Route> route;

    public UserFavorite(String username, String from, String to, TypeOfTravel typeOfTravel, List<Route> route) {
        this.username = username;
        this.from = from;
        this.to = to;
        this.typeOfTravel = typeOfTravel;
        this.route = route;
    }
}