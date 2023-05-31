package com.votecounter.service;

import com.votecounter.domain.ImageFile;
import com.votecounter.domain.Party;
import com.votecounter.dto.request.PartyCreateRequest;
import com.votecounter.dto.response.PartyResponse;
import com.votecounter.exception.ConflictException;
import com.votecounter.exception.ResourceNotFoundException;
import com.votecounter.exception.message.ErrorMessage;
import com.votecounter.repository.ImageFileRepository;
import com.votecounter.repository.PartyRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PartyService {
    private final PartyRepository partyRepository;
    private final ImageFileService imageFileService;
    private final ImageFileRepository imageFileRepository;

    public PartyService(PartyRepository partyRepository, ImageFileService imageFileService, ImageFileRepository imageFileRepository) {
        this.partyRepository = partyRepository;
        this.imageFileService = imageFileService;
        this.imageFileRepository = imageFileRepository;
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
        List<ImageFile> imFiles = new ArrayList<>();
        imFiles.add(imageFile);
        party.setImage(imFiles);

        party.setPartyName(partyCreateRequest.getPartyName());
        partyRepository.save(party);


    }

    // get All parties
    public List<PartyResponse> getAllParties() {
        List<PartyResponse> DTOParties = new ArrayList<>();// Clienta gönderilecek list oluşturdum
        List<Party> pojoParties = partyRepository.findAll(); // DB den gelecek pojo tipindeki partileri listeledim
        PartyResponse partyResponse =new PartyResponse();
        for (Party w : pojoParties) {// DB den gelen partilerin olduğu listten sırayla partileri çağırıp DTO ya çevirdim

            partyResponse.setPartyName(w.getPartyName());

            DTOParties.add(partyResponse);
        }
        //imageFile ları çağırıp yalnızca IDlerini  client a göndereceğim
        List<PartyResponse>DtoImgFileIDs= new ArrayList<>();
        PartyResponse imgFileId = new PartyResponse();
        List<ImageFile>imgFiles = imageFileRepository.findAll();
        for (ImageFile x : imgFiles){
            imgFileId.setImage(Collections.singletonList(x.getId()));
            DtoImgFileIDs.add(imgFileId);
        }
        DTOParties.addAll(DtoImgFileIDs);

       return DTOParties;
    }
// get a party byId
    public PartyResponse getParty(Long id) {
      Party pojoParty = partyRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(
                        String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, id)));

        PartyResponse DTOParty = new PartyResponse();
        DTOParty.setPartyName(pojoParty.getPartyName());
                 return DTOParty;
    }

    //findById==>> asistant method

}
