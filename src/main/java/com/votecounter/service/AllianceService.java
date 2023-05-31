package com.votecounter.service;

import com.votecounter.domain.Alliance;
import com.votecounter.domain.Party;
import com.votecounter.dto.request.AllianceRequest;
import com.votecounter.dto.response.VtResponse;
import com.votecounter.exception.ConflictException;
import com.votecounter.exception.message.ErrorMessage;
import com.votecounter.repository.AllianceRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AllianceService {
   /* private final AllianceRepository allianceRepository;

    public AllianceService(AllianceRepository allianceRepository) {
        this.allianceRepository = allianceRepository;
    }

    public VtResponse allianceSave(AllianceRequest allianceRequest) {
        //is there any such an alliance with same name??
        if(allianceRepository.existsByName(allianceRequest.getAllianceName())){
            throw  new ConflictException(
                    String.format(ErrorMessage.THIS_ALLIANCE_NAME_ALREADY_EXIST_MESSAGE,
                            allianceRequest.getAllianceName())
            );
        }
        Set<Party> partyList = new HashSet<>();
        //party listesini getir. partyList setine add. alliance a setle
        Alliance alliance = new Alliance();

        return null;
    }*/
}
