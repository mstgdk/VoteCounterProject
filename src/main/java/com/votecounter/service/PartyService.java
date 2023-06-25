package com.votecounter.service;

import com.votecounter.domain.Alliance;
import com.votecounter.domain.ImageFile;
import com.votecounter.domain.Party;
import com.votecounter.dto.request.PartyCreateRequest;
import com.votecounter.dto.request.PartyUpdateRequest;
import com.votecounter.dto.response.PartyResponse;
import com.votecounter.exception.ConflictException;
import com.votecounter.exception.ResourceNotFoundException;
import com.votecounter.exception.message.ErrorMessage;
import com.votecounter.repository.AllianceRepository;
import com.votecounter.repository.ImageFileRepository;
import com.votecounter.repository.PartyRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class PartyService {
    private final PartyRepository partyRepository;
    private final ImageFileService imageFileService;
    private final ImageFileRepository imageFileRepository;
    private final AllianceRepository allianceRepository;
    private final AllianceService allianceService;


    public PartyService(PartyRepository partyRepository, ImageFileService imageFileService, ImageFileRepository imageFileRepository, AllianceRepository allianceRepository, @Lazy AllianceService allianceService) {
        this.partyRepository = partyRepository;
        this.imageFileService = imageFileService;
        this.imageFileRepository = imageFileRepository;
        this.allianceRepository = allianceRepository;
        this.allianceService = allianceService;
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
        PartyResponse partyResponse = new PartyResponse();
        for (Party w : pojoParties) {// DB den gelen partilerin olduğu listten sırayla partileri çağırıp DTO ya çevirdim

            partyResponse.setPartyName(w.getPartyName());

            DTOParties.add(partyResponse);
        }
        //imageFile ları çağırıp yalnızca IDlerini  client a göndereceğim
        List<PartyResponse> DtoImgFileIDs = new ArrayList<>();
        PartyResponse imgFileId = new PartyResponse();
        List<ImageFile> imgFiles = imageFileRepository.findAll();
        for (ImageFile x : imgFiles) {
            imgFileId.setImage(Collections.singletonList(x.getId()));
            DtoImgFileIDs.add(imgFileId);
        }
        DTOParties.addAll(DtoImgFileIDs);

        return DTOParties;
    }

    // get a party byId
    public PartyResponse getParty(Long id) {
        Party pojoParty = partyRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(
                        String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, id)));

        PartyResponse DTOParty = new PartyResponse();
        DTOParty.setPartyName(pojoParty.getPartyName());
        return DTOParty;
    }

    public PartyResponse getPartyAndImage(Long id) {
        Party pojoParty = partyRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(
                        String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, id)));

        // Image File
        ImageFile imageFile = imageFileService.findImageByPartyId(id); //  bir party ye birden fazla image atanınca NoUniqResult hatası veriyor
        List<String> imFiles = new ArrayList<>();
        imFiles.add(imageFile.getId());
        //------------
        PartyResponse dtoParty = new PartyResponse();
        dtoParty.setPartyName(pojoParty.getPartyName());
        dtoParty.setImage(imFiles);
        //-----Party ye Alliance eklenecek
        Long allianceId = partyRepository.findAllianceId(id);
        if (allianceId == null) {
            dtoParty.setAllianceMessage("This party has no Alliance");
        } else {
            Alliance alliance = allianceRepository.findAllianceById(allianceId);
            dtoParty.setAlliance(alliance);
        }
        return dtoParty;
    }


    public List<PartyResponse> getAllPartiesWithImage() {
        List<PartyResponse> DTOParties = new ArrayList<>();// Clienta gönderilecek list oluşturdum
        List<Party> pojoParties = partyRepository.findAll(); // DB den gelecek pojo tipindeki partileri listeledim

        for (Party w : pojoParties) {// DB den gelen partilerin olduğu listten sırayla partileri çağırıp DTO ya çevirdim
            PartyResponse partyResponse = new PartyResponse();
            partyResponse.setPartyName(w.getPartyName());
            ImageFile imageFile = imageFileService.findImageByPartyId(w.getId());
            List<String> imageID = new ArrayList<>();
            imageID.add(imageFile.getId());
            partyResponse.setImage(imageID);
            //alliance bilgisi de setlenmeli*******
            Long allianceId = partyRepository.findAllianceId(w.getId());
            if (allianceId == null) {
                partyResponse.setAllianceMessage("This party has no Alliance");
            } else {
                Alliance alliance = allianceRepository.findAllianceById(allianceId);
                partyResponse.setAlliance(alliance);
            }

            DTOParties.add(partyResponse);

        }


        return DTOParties;
    }

    // updateParty
    public void updateParty(Long id, PartyUpdateRequest partyUpdateRequest) {
        Party party = partyRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(
                        String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, id)));
        if (partyUpdateRequest.getPartyName() == null) {
            party.setPartyName(party.getPartyName());
        } else {
            party.setPartyName(partyUpdateRequest.getPartyName());// party nin ismini update ettik
        }


        Alliance alliance = allianceRepository.findAllianceByAllianceName(partyUpdateRequest.getJoinAlliance());
        party.setAlliance(alliance);

        partyRepository.save(party);
    }

    //yardımcı metot
    public List<Party> parties(Long id) {
        List<Party> partyList = partyRepository.findAllByAllianceId(id);
        return partyList;
    }

    public List<String> getPartyNames(Long id) {
        List<String> partyList = partyRepository.findAllWithAllianceId(id);
        return partyList;
    }

    public Party findById(Long partyId) {
        Party party = partyRepository.findById(partyId).orElseThrow(() ->
                new ResourceNotFoundException(
                        String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, partyId)));
        return party;
    }

    public List<PartyResponse> getAlliedPartiesOfAParty(Long id) {
        Party party = partyRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(
                        String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, id)));
        if (party.getAlliance() == null) {//if the party isn't in an alliance, execution will end.
            //yeni responsClass oluştur. mesaj gönder: partinin aliance i yok
            //Fikir.--> allianceId sini bul. yoksa exception atsın
        }
    }
}
