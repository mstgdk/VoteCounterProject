package com.votecounter.controller;

import com.votecounter.dto.request.VoteCreateRequest;
import com.votecounter.dto.response.ResponseMessage;
import com.votecounter.dto.response.VoteResponse;
import com.votecounter.dto.response.VtResponse;
import com.votecounter.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vote")
public class VoteController {
    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("/{partyId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VtResponse>registerVote(@PathVariable Long partyId){
        voteService.registerVote(partyId);

        VtResponse response=new VtResponse();
        response.setSuccess(true);
        response.setMessage(ResponseMessage.VOTE_REGISTER_RESPONSE_MESSAGE);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/totalvotes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VoteResponse>results(){
      VoteResponse response =   voteService.results();
      return ResponseEntity.ok(response);
    }
}
