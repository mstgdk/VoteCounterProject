package com.votecounter.repository;

import com.votecounter.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByCitNumber(String citNumber);

    @EntityGraph(attributePaths = "roles") //Defaultta Lazy olan Role bilgilerini EAGER yaptık. sadece bu metot çalıştığında eager olur
    Optional<User> findByCitNumber(String citNumber);
}
