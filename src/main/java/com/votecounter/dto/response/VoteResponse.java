package com.votecounter.dto.response;

import com.votecounter.domain.Alliance;
import com.votecounter.domain.Candidate;
import com.votecounter.domain.Party;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoteResponse {
    private int totalNumberOfVotes;
    private Candidate winnerCandidate;
    private int NumOfVotesOfWinnerCandidate;
    private String winnerParty;
    private int NumOfVotesOfWinnerParty;
    private Alliance winnerAlliance;
    private int NumOfVotesOfWinnerAlliance;
}
