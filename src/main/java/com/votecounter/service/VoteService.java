package com.votecounter.service;

import com.votecounter.domain.Party;
import com.votecounter.domain.Vote;
import com.votecounter.dto.response.VoteResponse;
import com.votecounter.exception.ResourceNotFoundException;
import com.votecounter.exception.message.ErrorMessage;
import com.votecounter.repository.PartyRepository;
import com.votecounter.repository.VoteRepository;
import org.springframework.stereotype.Service;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final PartyRepository partyRepository;
    private final PartyService partyService;

    public VoteService(VoteRepository voteRepository, PartyRepository partyRepository, PartyService partyService) {
        this.voteRepository = voteRepository;
        this.partyRepository = partyRepository;
        this.partyService = partyService;
    }

    public void registerVote(Long partyId) {
        Vote vote = new Vote();
        Party party = partyRepository.findById(partyId).orElseThrow(() ->
                new ResourceNotFoundException(
                        String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, partyId)));
        vote.setParty(party);
        voteRepository.save(vote);
    }

    public VoteResponse results() {
        VoteResponse voteResponse = new VoteResponse();
        //totalNumberOfVotes
        int totalNumOfVotes = voteRepository.totalNumOfVotes();//repo katmanına QUERY yaz
        voteResponse.setTotalNumberOfVotes(totalNumOfVotes);
        //winnerParty
        Long party_id=voteRepository.winnerParty();
         String winnerParty = partyService.getPartyName(party_id);
        voteResponse.setWinnerParty(winnerParty);
        //winnerCandidate

        //winnerAlliance
        //numOfVotesOfWinnerAlliance
        // numOfVotesOfWinnerCandidate
        // numOfVotesOfWinnerParty






    return voteResponse;
    }
}
