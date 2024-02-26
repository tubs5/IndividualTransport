package org.example.indivudualtransport.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.indivudualtransport.Model.route.Route;

/**
 * @author Tobias Heidlund
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(name = "uniqueUsernameRoute",columnNames = {"username","route"})})
public class UserFavorite {
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String username;
    @OneToOne
    Route route;
}