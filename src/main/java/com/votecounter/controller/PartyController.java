package com.votecounter.controller;

import com.votecounter.dto.request.PartyCreateRequest;
import com.votecounter.dto.response.ResponseMessage;
import com.votecounter.dto.response.VtResponse;
import com.votecounter.service.PartyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/party")
public class PartyController {
    private  final PartyService partyService;

    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }
// PartyA imageId = 8a8e80828859333801885935c8c00000
// PartyB imageId = 8a8e8082885991000188599235ff0000
@PostMapping("/admin/{imageId}/create")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<VtResponse> saveCar(
        @PathVariable String imageId, @Valid @RequestBody PartyCreateRequest partyCreateRequest) {
    partyService.saveParty(imageId, partyCreateRequest);

    VtResponse response = new VtResponse(
            ResponseMessage.PARTY_SAVED_RESPONSE_MESSAGE, true);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
}
}