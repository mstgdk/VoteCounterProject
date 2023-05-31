package com.votecounter.controller;

import com.votecounter.dto.request.AllianceRequest;
import com.votecounter.dto.response.VtResponse;
import com.votecounter.service.AllianceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alliance")
public class AllianceController {
    /*private final AllianceService allianceService;

    public AllianceController(AllianceService allianceService) {
        this.allianceService = allianceService;
    }

    @PostMapping("/alliance/create")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<VtResponse>allianceCreate(@RequestBody AllianceRequest allianceRequest){
                  VtResponse response = allianceService.allianceSave(allianceRequest);

                  return ResponseEntity.ok(response);
    }*/

}
