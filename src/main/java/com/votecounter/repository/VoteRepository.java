package com.votecounter.repository;

import com.votecounter.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    @Query(value = "SELECT count(*) from t_vote", nativeQuery = true)
    int totalNumOfVotes();
    @Query(value = "SELECT party_id, count(id) as numberOfVotes FROM t_vote\n" +
                   "GROUP BY (party_id)\n" +
                   "ORDER BY numberOfVotes desc limit 1", nativeQuery = true)
    Long winnerParty();

    @Query(value = "SELECT count(id) as numberOfVotes FROM t_vote\n" +
            "GROUP BY (party_id)\n" +
            "ORDER BY numberOfVotes desc limit 1", nativeQuery = true)
    int numOfVotesOfWinnerParty();

    @Query(value = "select  count(id) as sonuc, alliance_id from\n" +
            "(select t_vote.id, party.alliance_id from t_vote full join party on t_vote.party_id=party.id) as X\n" +
            "group by (alliance_id)\n" +
            "order by sonuc desc limit 1", nativeQuery = true)
    int numOfVotesOfWinnerAlliance();
}
