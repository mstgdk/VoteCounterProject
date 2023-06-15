package com.votecounter.controller;

import com.votecounter.dto.request.AllianceRequest;
import com.votecounter.dto.response.AllianceResponse;
import com.votecounter.dto.response.AllianceWithPartyListResponse;
import com.votecounter.dto.response.ResponseMessage;
import com.votecounter.dto.response.VtResponse;
import com.votecounter.service.AllianceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alliance")
public class AllianceController {
    private final AllianceService allianceService;

    public AllianceController(AllianceService allianceService) {
        this.allianceService = allianceService;
    }

    //create Alliance
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<VtResponse>allianceCreate(@RequestBody AllianceRequest allianceRequest){
                  allianceService.allianceSave(allianceRequest);
                  VtResponse response = new VtResponse();
                  response.setSuccess(true);
                  response.setMessage(ResponseMessage.ALLIANCE_SAVED_RESPONSE_MESSAGE);
                  return ResponseEntity.ok(response);
    }
   // get An Alliance by Id
    @GetMapping("/{id}")
    public ResponseEntity<AllianceWithPartyListResponse>getAnAllianceById(@PathVariable Long id){
        AllianceWithPartyListResponse allianceResponse=allianceService.getAllianceById(id);
        return ResponseEntity.ok(allianceResponse);
    }
    // get An Alliance with Id
    @GetMapping("/alliance/{id}")
    public ResponseEntity<AllianceResponse>getAnAllianceWithId(@PathVariable Long id){
        AllianceResponse allianceResponse=allianceService.getAllianceWithId(id);
        return ResponseEntity.ok(allianceResponse);
    }
    //Delete Alliance
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VtResponse>deleteAlliance(@PathVariable Long id){
        allianceService.deleteAlliance(id);

        VtResponse response = new VtResponse();
        response.setSuccess(true);
        response.setMessage(ResponseMessage.ALLIANCE_DELETED_RESPONSE_MESSAGE);
        return ResponseEntity.ok(response);
    }
    //get all parties of an Alliance

}
