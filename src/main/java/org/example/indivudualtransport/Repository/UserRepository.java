package org.example.indivudualtransport.Repository;

import org.example.indivudualtransport.Model.UserFavorite;
import org.example.indivudualtransport.Model.route.TypeOfTravel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserFavorite,Long> {

    List<UserFavorite> getByUsername(String username);
    void deleteByUsernameAndFromAndToAndTypeOfTravel(String username, String from, String to, TypeOfTravel typeOfTravel);
    UserFavorite getByUsernameAndFromAndToAndTypeOfTravel(String username, String from, String to, TypeOfTravel typeOfTravel);

}
