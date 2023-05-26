package com.votecounter.repository;

import com.votecounter.domain.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {
    @Query("SELECT count(*) from Party p join p.image img where img.id=:id")
    Integer findPartyCountByImageId(@Param("id") String id);
}
