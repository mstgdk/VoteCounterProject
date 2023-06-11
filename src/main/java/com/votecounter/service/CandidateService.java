package com.votecounter.service;

import com.votecounter.domain.Candidate;
import com.votecounter.domain.Party;
import com.votecounter.dto.request.CandidateRequest;
import com.votecounter.dto.response.CandidateResponse;
import com.votecounter.repository.CandidateRepository;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final PartyService partyService;

    public CandidateService(CandidateRepository candidateRepository, PartyService partyService) {
        this.candidateRepository = candidateRepository;
        this.partyService = partyService;
    }

    public void createCandidate(CandidateRequest candidateRequest) {
        Candidate candidate =new Candidate();
        candidate.setFirstName(candidateRequest.getFirstName());
        candidate.setLastName(candidateRequest.getLastName());
        if (candidateRequest.getPartyId()!=null){
            Party party =partyService.findById(candidateRequest.getPartyId());
            candidate.setParty(party);
        }
        candidateRepository.save(candidate);
    }
}
