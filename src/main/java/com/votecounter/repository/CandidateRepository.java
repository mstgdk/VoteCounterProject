package com.votecounter.repository;

import com.votecounter.domain.Candidate;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate,Long> {
    @Query(value = "select * from candidate where party_id in (select party_id from party where alliance_id=:id)", nativeQuery = true)
    List<Candidate> findAllCandidatedOfAlliance(@Param("id") Long id);

    @EntityGraph(attributePaths = "party")
    @Query("select c from Candidate c where c.party.id=:id")
   // @Query(value = "select * from candidate where party_id=:id", nativeQuery = true)
    Candidate getCandidateByPartyId(@Param("id") Long partyId);

    @Query(value = "select c from Candidate  c where c.party.id=:partyId")
    Candidate isExistsCandidateByPartyId(@Param("partyId") Long partyId);
}
/*
"message": "org.hibernate.QueryException: Space is not allowed after parameter
 prefix ':' [select * from candidate where party_id in (select party_id from party where alliance_id:=id)]; nested exception
  is java.lang.IllegalArgumentException: org.hibernate.QueryException: Space is not allowed
after parameter prefix ':' [select * from candidate where party_id in (select party_id from party where alliance_id:=id)]",
 */