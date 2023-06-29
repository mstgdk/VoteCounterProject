package com.votecounter.repository;

import com.votecounter.domain.Alliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface AllianceRepository extends JpaRepository<Alliance, Long> {
    @Query(value = "select * from t_alliance where t_alliance.alliance_name=:name", nativeQuery = true)
    Alliance findAllianceByAllianceName(@Param("name") String name);

    @Query(value = "select * from t_alliance where t_alliance.id=:id", nativeQuery = true)
    Alliance findAllianceById(@Param("id") Long allianceId);


}
