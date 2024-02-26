package org.example.indivudualtransport.Repository;

import org.example.indivudualtransport.Model.Route;
import org.example.indivudualtransport.Model.UserFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserFavorite,Long> {

    List<UserFavorite> getByUsername(String username);
    void deleteByUsernameAndRoute(String username, Route route);
    UserFavorite getByUsernameAndRoute(String username, Route route);

}
