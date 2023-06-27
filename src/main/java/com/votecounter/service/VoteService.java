package com.votecounter.service;

import com.votecounter.domain.Party;
import com.votecounter.domain.Vote;
import com.votecounter.exception.ResourceNotFoundException;
import com.votecounter.exception.message.ErrorMessage;
import com.votecounter.repository.PartyRepository;
import com.votecounter.repository.VoteRepository;
import org.springframework.stereotype.Service;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final PartyRepository partyRepository;

    public VoteService(VoteRepository voteRepository, PartyRepository partyRepository) {
        this.voteRepository = voteRepository;
        this.partyRepository = partyRepository;
    }

    public void registerVote(Long partyId) {
        Vote vote = new Vote();
        Party party = partyRepository.findById(partyId).orElseThrow(() ->
                new ResourceNotFoundException(
                        String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, partyId)));
        vote.setParty(party);
        voteRepository.save(vote);
    }
}
