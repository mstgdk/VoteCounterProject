package com.votecounter.controller;

import com.votecounter.domain.ImageFile;
import com.votecounter.dto.response.ImageFileResponse;
import com.votecounter.dto.response.ImageSavedResponse;
import com.votecounter.dto.response.ResponseMessage;
import com.votecounter.service.ImageFileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ImageFileResponse>> getAllImages(){
        List<ImageFileResponse> allImageDTO = imageFileService.getAllImages();

        return ResponseEntity.ok(allImageDTO);
    }
    //DOWNLOAD
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String id){//byte arraylar zaten fotoğraflarımdır. Bu yüzden dönen  byte[] olmalı
        ImageFile imageFile= imageFileService.getImageById(id); // Tüm çağırmaları ImageFile üzerinden yaptığımız için ImageFile gelecek

        return ResponseEntity.ok().header(//body de ne göndereceksem onları header a koyarım
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=" + imageFile.getName()).//indirirken DB'ki isim otomatik gelir. Aksi durumda indirirken biz dosya ismini belirtmemizz gerekir
                body(imageFile.getImageData().getData());
    }
    //Image DISPLAY
    @GetMapping("/display/{id}")
    public ResponseEntity<byte[]> displayFile(@PathVariable String id){
        ImageFile imageFile= imageFileService.getImageById(id);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(imageFile.getImageData().getData(),
                header,
                HttpStatus.OK);
    }
}