package com.votecounter.repository;

import com.votecounter.domain.Alliance;
import com.votecounter.domain.Party;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {
    @Query("SELECT count(*) from Party p join p.image img where img.id=:id")
    Integer findPartyCountByImageId(@Param("id") String id);

    //@EntityGraph(attributePaths = "alliance_id")
     //@Query("SELECT p from Party p join p.alliance pa where pa.id=:id")
    //@Query(value = "select * from party where party.alliance_id=:id", nativeQuery = true)
    List<Party> findAllByAllianceId(@Param("id") Long id);


    @Query(value = "select party_name from party where party.alliance_id=:id", nativeQuery = true)
    //@Query("SELECT p.partyName from Party p join p.alliance pa where pa.id=:id")
    List<String> findAllWithAllianceId(Long id);


}
