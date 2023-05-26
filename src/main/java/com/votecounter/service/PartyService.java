package com.votecounter.service;

import com.votecounter.domain.ImageFile;
import com.votecounter.domain.Party;
import com.votecounter.dto.request.PartyCreateRequest;
import com.votecounter.exception.ConflictException;
import com.votecounter.exception.message.ErrorMessage;
import com.votecounter.repository.PartyRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PartyService {
    private final PartyRepository partyRepository;
    private final ImageFileService imageFileService;

    public PartyService(PartyRepository partyRepository, ImageFileService imageFileService) {
        this.partyRepository = partyRepository;
        this.imageFileService = imageFileService;
    }


    public void saveParty(String imageId, PartyCreateRequest partyCreateRequest) {
        //!!! image Id , Repo da var mi ??
        ImageFile imageFile = imageFileService.findImageById(imageId);
        //!!! imageId daha once baska bir arac icin kullanildi mi ???
        Integer usedPartyCount = partyRepository.findPartyCountByImageId(imageFile.getId());
        if (usedPartyCount > 0) {
            throw new ConflictException(ErrorMessage.IMAGE_USED_MESSAGE);
        }
        //!!! mapper islemi
        Party party = new Party();

        //!!! image bilgisini party'ye ekliyoruz
        Set<ImageFile> imFiles = new HashSet<>();
        imFiles.add(imageFile);
        party.setImage(imFiles);
        party.setPartyName(partyCreateRequest.getPartyName());
        partyRepository.save(party);


    }
}
