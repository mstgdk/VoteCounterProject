package com.votecounter.controller;

import com.votecounter.dto.request.CandidateRequest;
import com.votecounter.dto.response.CandidateResponse;
import com.votecounter.dto.response.ResponseMessage;
import com.votecounter.dto.response.VtResponse;
import com.votecounter.service.CandidateService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
