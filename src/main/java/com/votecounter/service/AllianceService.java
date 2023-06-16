package com.votecounter.service;

import com.votecounter.domain.Alliance;
import com.votecounter.domain.Candidate;
import com.votecounter.dto.request.AllianceRequest;
import com.votecounter.dto.response.AllCandidatesResponse;
import com.votecounter.dto.response.AllianceResponse;
import com.votecounter.exception.ConflictException;
import com.votecounter.exception.ResourceNotFoundException;
import com.votecounter.exception.message.ErrorMessage;
import com.votecounter.repository.AllianceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AllianceService {
    private final AllianceRepository allianceRepository;
    private final PartyService partyService;
    private final CandidateService candidateService;

    public AllianceService(AllianceRepository allianceRepository, PartyService partyService, CandidateService candidateService) {
        this.allianceRepository = allianceRepository;
        this.partyService = partyService;
        this.candidateService = candidateService;
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

//    public AllianceWithPartyListResponse getAllianceById(Long id) {
//        Alliance alliance=allianceRepository.findById(id).orElseThrow(()->
//                new ResourceNotFoundException(
//                        String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, id)));
//        //Bussiness Logic: pojo To DTO
//        AllianceWithPartyListResponse allianceWithPartyListResponse = new AllianceWithPartyListResponse();
//        allianceWithPartyListResponse.setId(alliance.getId());
//        allianceWithPartyListResponse.setAllianceName(alliance.getAllianceName());
//
//        //Alliance a party ekleyeceğiz
//
//        List<Party> partyList=partyService.parties(id);
//        allianceWithPartyListResponse.setPartyList(partyList);
//
//        return allianceWithPartyListResponse;
//        /*
//        Could not write JSON: Unable to access lob stream; nested exception is com.fasterxml.jackson.databind.JsonMappingException:
//        Unable to access lob stream (through reference chain:
//        com.votecounter.dto.response.AllianceResponse[\"partyList\"]->java.util.ArrayList[0]->com.votecounter.domain.Party[\"image\"])"
//         */
//    }

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


    public void updateAlliance(Long id, AllianceRequest allianceRequest) {
        Alliance alliance=allianceRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(
                        String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, id)));
        alliance.setAllianceName(allianceRequest.getAllianceName());
        allianceRepository.save(alliance);
    }

    public List<Candidate> getAllCandidatesBelongToAnAllianceById(Long id) {
        List<Candidate>candidates=candidateService.getAllCandidatedOfAlliance(id);//buradan candidate service->candidateRepository->Query yazılacak

        return candidates;
    }
}
