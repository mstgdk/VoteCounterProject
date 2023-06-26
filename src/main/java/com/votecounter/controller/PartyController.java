package com.votecounter.controller;

import com.votecounter.domain.Party;
import com.votecounter.dto.request.PartyCreateRequest;
import com.votecounter.dto.request.PartyUpdateRequest;
import com.votecounter.dto.response.PartyResponse;
import com.votecounter.dto.response.ResponseMessage;
import com.votecounter.dto.response.VtResponse;
import com.votecounter.service.PartyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/party")
public class PartyController {
    private final PartyService partyService;

    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    // PartyA imageId = 8a8e80828859333801885935c8c00000
// PartyB imageId = 8a8e80828869188d01886919f8f40000
    // c = 8a8e8082886942bb018869433f4e0000
    @PostMapping("/admin/{imageId}/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VtResponse> saveParty(
            @PathVariable String imageId, @Valid @RequestBody PartyCreateRequest partyCreateRequest) {
        partyService.saveParty(imageId, partyCreateRequest);

        VtResponse response = new VtResponse(
                ResponseMessage.PARTY_SAVED_RESPONSE_MESSAGE, true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //get a party by ID (without Image)
    @GetMapping("/{id}")
    public ResponseEntity<PartyResponse> getParty(@PathVariable Long id){
        PartyResponse party = partyService.getParty(id);
        return ResponseEntity.ok(party);
    }
    //get a party by ID (with Image)
    @GetMapping("/partywithimage/{id}")
    public ResponseEntity<PartyResponse> getPartyAndImage(@PathVariable Long id){
        PartyResponse party = partyService.getPartyAndImage(id);
        return ResponseEntity.ok(party);
    }

    // List All parties
    @GetMapping("/getAll")
    public ResponseEntity<List<PartyResponse>> getAllParties(){
        List<PartyResponse>parties = partyService.getAllParties();
        return ResponseEntity.ok(parties);
    }
    @GetMapping("/getAllWithImage")
    public ResponseEntity<List<PartyResponse>> getAllPartiesWithImage(){
        List<PartyResponse>parties = partyService.getAllPartiesWithImage();
        return ResponseEntity.ok(parties);
    }
    // UPDATE
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VtResponse>updateParty(@PathVariable Long id, @Valid @RequestBody PartyUpdateRequest partyUpdateRequest){
        partyService.updateParty(id,partyUpdateRequest);
        VtResponse response = new VtResponse();
        response.setSuccess(true);
        response.setMessage(ResponseMessage.PARTY_UPDATED_MESSAGE);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/parties/{id}")// This is the party ID which you want its allied parties
    public ResponseEntity<List<String>> getAlliedPartiesOfAParty(@PathVariable Long id){
        List<String>parties = partyService.getAlliedPartiesOfAParty(id);
        return ResponseEntity.ok(parties);
    }
}
