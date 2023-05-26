package com.votecounter.controller;

import com.votecounter.dto.response.ImageSavedResponse;
import com.votecounter.dto.response.ResponseMessage;
import com.votecounter.service.ImageFileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class ImageFileController {
    private final ImageFileService imageFileService;

    public ImageFileController(ImageFileService imageFileService) {
        this.imageFileService = imageFileService;
    }

    //!!!!  UPLOAD
    //Önce Image sisteme yüklenir, sonra ID döner. ID üzerinden car objesi DB'ye kaydedilir
    //ImageId: 8a8e8082869453000186945343200000
    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ImageSavedResponse> uploadFile(
            @RequestParam("file") MultipartFile file) {//görsel dosyanın parçalanmış halde gönderilmesini sağlar.(Parça derken ismi, bytları vs.)
        // Requestten gelecek görsel dosyayı MultipartFile türünde file dosyası ile maple
        // POSTMAN'de Body-key bölümüne file yazacağız
        String imageId = imageFileService.saveImage(file);
        ImageSavedResponse response =
                new ImageSavedResponse(imageId, ResponseMessage.IMAGE_SAVED_RESPONSE_MESSAGE, true);
        return ResponseEntity.ok(response);
    }
}