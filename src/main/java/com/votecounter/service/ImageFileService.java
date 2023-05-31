package com.votecounter.service;

import com.votecounter.domain.ImageData;
import com.votecounter.domain.ImageFile;
import com.votecounter.dto.response.ImageFileResponse;
import com.votecounter.exception.ResourceNotFoundException;
import com.votecounter.exception.message.ErrorMessage;
import com.votecounter.repository.ImageFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ImageFileService {
    private final ImageFileRepository imageFileRepository;

    public ImageFileService(ImageFileRepository imageFileRepository) {
        this.imageFileRepository = imageFileRepository;
    }

    public String saveImage(MultipartFile file) {//ID dönmeli

        ImageFile imageFile = null;//önce boş oluşturuyoruz
        // !!! name
        String fileName =//önce dosyanın ismine ulaşmalıyım ki kllanıcı aynı isimde kaydetmek isteyebilir
                StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        //cleanPath--Parçalı gelen dosyadan istediğimz bilgileri almamızı sağlar
        //!!! Data
        try {
            ImageData imData = new ImageData(file.getBytes());
            imageFile = new ImageFile(fileName, file.getContentType(), imData);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        imageFileRepository.save(imageFile);

        return imageFile.getId();//Id döndürüyoruz
    }

    public ImageFile getImageById(String id) {
        ImageFile imageFile = imageFileRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE, id)));
        return imageFile;
    }
    public ImageFile findImageById(String imageId) {
        return imageFileRepository.findImageById(imageId).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE,imageId)));
    }
    // get All Images
    public List<ImageFileResponse> getAllImages() {
        List<ImageFile> imageFiles = imageFileRepository.findAll();
        // image1 : localhost:8080/files/download/id

        List<ImageFileResponse> imageFileDTOS =imageFiles.stream().map(imFile->{
            // URI olusturulmasini sagliyacagiz
            String imageUri = ServletUriComponentsBuilder.//URI üretmeeye yarar
                    fromCurrentContextPath(). // localhost:8080
                    path("/files/download/"). // localhost:8080/files/download
                    path(imFile.getId()).toUriString();// localhost:8080/files/download/id
            return new ImageFileResponse(imFile.getName(),
                    imageUri,
                    imFile.getType(),
                    imFile.getLength());
        }).collect(Collectors.toList());

        return imageFileDTOS;
    }

    public ImageFile findImageByPartyId(Long id) {
        ImageFile imageFile = imageFileRepository.findImageByPartyId(id);
        return imageFile;
    }
}
