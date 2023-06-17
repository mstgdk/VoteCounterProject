package com.votecounter.repository;

import com.votecounter.domain.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate,Long> {
    @Query(value = "select * from candidate where party_id in (select party_id from party where alliance_id=:id)", nativeQuery = true)
    List<Candidate> findAllCandidatedOfAlliance(@Param("id") Long id);
}
/*
"message": "org.hibernate.QueryException: Space is not allowed after parameter
 prefix ':' [select * from candidate where party_id in (select party_id from party where alliance_id:=id)]; nested exception
  is java.lang.IllegalArgumentException: org.hibernate.QueryException: Space is not allowed
after parameter prefix ':' [select * from candidate where party_id in (select party_id from party where alliance_id:=id)]",
 */