package com.votecounter.service;

import com.votecounter.domain.Candidate;
import com.votecounter.domain.Party;
import com.votecounter.dto.request.CandidateRequest;
import com.votecounter.dto.response.CandidateResponse;
import com.votecounter.dto.response.ResponseMessage;
import com.votecounter.exception.ResourceNotFoundException;
import com.votecounter.exception.message.ErrorMessage;
import com.votecounter.repository.CandidateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void deleteCandidate(Long id) {
        Candidate candidate = candidateRepository.findById(id).orElseThrow(()->
                        new ResourceNotFoundException(
                                String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, id)));

        candidateRepository.delete(candidate);
    }

    public CandidateResponse getCandidateById(Long id) {
        Candidate candidate = candidateRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION,id)));

        CandidateResponse candidateResponse =new CandidateResponse();
        candidateResponse.setId(candidate.getId());
        candidateResponse.setFirstName(candidate.getFirstName());
        candidateResponse.setLastName(candidate.getLastName());
        if (candidate.getParty()!=null){
            candidateResponse.setPartyName(candidate.getParty().getPartyName());
        } else {
            candidateResponse.setPartyName("This is an independent candidate");
        }


        return candidateResponse;
    }

    public List<Candidate> getAllCandidatedOfAlliance(Long id) {
        List<Candidate>candidates = candidateRepository.findAllCandidatedOfAlliance(id);
        return candidates;
    }
}
