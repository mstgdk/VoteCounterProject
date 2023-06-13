package com.votecounter.controller;

import com.votecounter.dto.request.CandidateRequest;
import com.votecounter.dto.response.CandidateResponse;
import com.votecounter.dto.response.ResponseMessage;
import com.votecounter.dto.response.VtResponse;
import com.votecounter.service.CandidateService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VtResponse> createCandidate(@Valid @RequestBody CandidateRequest candidateRequest) {
        candidateService.createCandidate(candidateRequest);
        VtResponse response = new VtResponse();
        response.setSuccess(true);
        response.setMessage(ResponseMessage.CANDIDATE_SAVED_MESSAGE);
        return ResponseEntity.ok(response);
    }
    //Delete Candidate
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VtResponse>deleteCandidate(@PathVariable Long id){
        candidateService.deleteCandidate(id);

        VtResponse response = new VtResponse();
        response.setSuccess(true);
        response.setMessage(ResponseMessage.CANDIDATE_DELETED_MESSAGE);
        return ResponseEntity.ok(response);
    }
    // get a candidate
    @GetMapping("/{id}")
    public ResponseEntity<CandidateResponse>getCandidateById(@PathVariable Long id){
          CandidateResponse candidateResponse = candidateService.getCandidateById(id);

          return ResponseEntity.ok(candidateResponse);
    }
}
