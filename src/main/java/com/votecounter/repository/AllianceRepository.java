package com.votecounter.repository;

import com.votecounter.domain.Alliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AllianceRepository extends JpaRepository<Alliance, Long> {
    //boolean existsByName(String name);

}
