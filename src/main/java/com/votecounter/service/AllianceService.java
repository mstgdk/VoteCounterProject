package com.votecounter.service;

import com.votecounter.domain.Alliance;
import com.votecounter.domain.Party;
import com.votecounter.dto.request.AllianceRequest;
import com.votecounter.dto.response.AllianceResponse;
import com.votecounter.dto.response.AllianceWithPartyListResponse;
import com.votecounter.dto.response.PartyResponse;
import com.votecounter.exception.ConflictException;
import com.votecounter.exception.ResourceNotFoundException;
import com.votecounter.exception.message.ErrorMessage;
import com.votecounter.repository.AllianceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class AllianceService {
    private final AllianceRepository allianceRepository;
    private final PartyService partyService;

    public AllianceService(AllianceRepository allianceRepository, PartyService partyService) {
        this.allianceRepository = allianceRepository;
        this.partyService = partyService;
    }

    public void allianceSave(AllianceRequest allianceRequest) {
        //is there any such an alliance with same name??
        Alliance isAlliance = allianceRepository.findAllianceByAllianceName(allianceRequest.getAllianceName());
        if (isAlliance!=null){
            throw  new ConflictException(
                    String.format(ErrorMessage.THIS_ALLIANCE_NAME_ALREADY_EXIST_MESSAGE,
                            allianceRequest.getAllianceName())
            );
        }



       Alliance alliance = new Alliance();
        alliance.setAllianceName(allianceRequest.getAllianceName());
        allianceRepository.save(alliance);

    }

    public AllianceWithPartyListResponse getAllianceById(Long id) {
        Alliance alliance=allianceRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(
                        String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, id)));
        //Bussiness Logic: pojo To DTO
        AllianceWithPartyListResponse allianceWithPartyListResponse = new AllianceWithPartyListResponse();
        allianceWithPartyListResponse.setId(alliance.getId());
        allianceWithPartyListResponse.setAllianceName(alliance.getAllianceName());

        //Alliance a party ekleyeceğiz

        List<Party> partyList=partyService.parties(id);
        allianceWithPartyListResponse.setPartyList(partyList);

        return allianceWithPartyListResponse;
        /*
        Could not write JSON: Unable to access lob stream; nested exception is com.fasterxml.jackson.databind.JsonMappingException:
        Unable to access lob stream (through reference chain:
        com.votecounter.dto.response.AllianceResponse[\"partyList\"]->java.util.ArrayList[0]->com.votecounter.domain.Party[\"image\"])"
         */
    }

    public AllianceResponse getAllianceWithId(Long id) {
        Alliance alliance=allianceRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(
                        String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, id)));
        //Bussiness Logic: pojo To DTO
        AllianceResponse allianceResponse = new AllianceResponse();
        allianceResponse.setId(alliance.getId());
        allianceResponse.setAllianceName(alliance.getAllianceName());

        //Alliance a party ekleyeceğiz

        List<String> partyList=partyService.getPartyNames(id);
        List<String>partyNamesList=new ArrayList<>();
        for (String w : partyList){
            partyNamesList.add(w);
        }

        allianceResponse.setPartyNamesList(partyNamesList);
        return allianceResponse;
    }

    public void deleteAlliance(Long id) {
        Alliance alliance=allianceRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(
                        String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, id)));
        allianceRepository.deleteById(id);
    }
}
