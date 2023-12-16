package com.springsecurityjwt.controller;


import com.springsecurityjwt.entity.ImageFile;
import com.springsecurityjwt.payload.response.ImageFileResponse;
import com.springsecurityjwt.repository.ImageFileRepository;
import com.springsecurityjwt.service.ImageFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/imageFiles")
@RequiredArgsConstructor
public class ImageFileController {

    private final ImageFileService imageFileService;
    private final ImageFileRepository imageFileRepository;

    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> uploadACarImage(@RequestParam("file") MultipartFile file) {
        return imageFileService.save(file);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<ImageFileResponse>> getAllCarsImage() {
        return imageFileService.getAllCarsImage();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity deleteACarImageById(@PathVariable Long id) {
        return imageFileService.deleteACarImageById(id);
    }


    @GetMapping(value = "/img/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable Long id) {
        ImageFile imageFile= imageFileService.findImageById(id);
        return imageFile.getData();
    }

    @GetMapping("/img/get/{userId}")
    public ImageFileResponse getACarsImage(@PathVariable Long userId) {
        return imageFileService.getACarsImageById(userId);
    }
}