package com.springsecurityjwt.service;


import com.springsecurityjwt.entity.ImageFile;
import com.springsecurityjwt.exception.BadRequestException;
import com.springsecurityjwt.exception.ResourceNotFoundException;
import com.springsecurityjwt.payload.mapper.ImageFileMapper;
import com.springsecurityjwt.payload.message.ErrorMessages;
import com.springsecurityjwt.payload.message.SuccessMessages;
import com.springsecurityjwt.payload.response.ImageFileResponse;
import com.springsecurityjwt.repository.ImageFileRepository;
import lombok.RequiredArgsConstructor;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageFileService {
    private final ImageFileRepository imageFileRepository;
    private final ImageFileMapper imageFileMapper;

    public ResponseEntity<String> save(MultipartFile file) {
        try {
            ImageFile imageFile = imageFileMapper.mapMultiPartFileToImageFile(file);
            imageFileRepository.save(imageFile);
            return ResponseEntity.ok(SuccessMessages.FILE_SAVE);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }
    }

    public ResponseEntity<List<ImageFileResponse>> getAllCarsImage() {
        List<ImageFileResponse> imageFileResponseList =
                imageFileRepository.
                        findAll().
                        stream().
                        map(imageFileMapper::mapImageFileToImageFileResponse).toList();
        return new ResponseEntity<>(imageFileResponseList, HttpStatus.OK);
    }

//    private HttpHeaders setTheCorrectContentType(ImageFileResponse img) {
//        HttpHeaders headers = new HttpHeaders();
//        MediaType contentType = switch (img.getContentType()) {
//            case MediaType.IMAGE_PNG_VALUE -> MediaType.IMAGE_PNG;
//            case MediaType.IMAGE_GIF_VALUE -> MediaType.IMAGE_GIF;
//            default -> MediaType.IMAGE_JPEG;
//        };
//        headers.setContentType(contentType);
//        return headers;
//    }

    public ResponseEntity deleteACarImageById(Long id) {

        boolean isDefault = imageFileRepository.isDefault(id);
        if(isDefault) {
            throw new BadRequestException(String.format(ErrorMessages.IMAGE_FILE_COULD_NOT_DELETED, id));
        }else {
            imageFileRepository.deleteById(id);
            return ResponseEntity.ok(SuccessMessages.IMAGE_FILE_DELETE);
        }
    }

    public ImageFileResponse getACarsImageById(Long id) {
        ImageFile imageFile = getImageFileById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(getMediaType(imageFile.getContentType()));
        ImageFileResponse imageFileResponse = imageFileMapper.mapImageFileToImageFileResponse(imageFile);
        // HttpHeaders head=setTheCorrectContentType(headers,imageFileResponse.getContentType());
        return imageFileResponse;
    }

    public ImageFile getImageFileById(Long id) {
        return imageFileRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.IMAGE_FILE_NOT_FOUND, id)));
    }

    public Set<ImageFile> getImagFilesByIdSet(Set<Long> imageFilesId) {
        return imageFilesId.stream().map(this::getImageFileById).collect(Collectors.toSet()); //TODO WHY GIVE ERROR  Unable to access lob stream] with root cause

    }

    @Transactional
    public void saveImageFromFile() throws IOException {
        // Load the image file from the classpath
        InputStream imageStream = getClass().getResourceAsStream("/static/ninja-car.gif");
        if (imageStream != null) {
            // Convert the image to a byte array
            byte[] imageBytes = IOUtils.toByteArray(imageStream);
            // Create an ImageFile entity
            ImageFile imageFile = ImageFile.builder()
                    .isDefault(true)
                    .data(imageBytes)
                    .contentType("image/gif")
                    .build();

            // Save the ImageFile entity to the database
            imageFileRepository.save(imageFile);
        }

    }

    public Set<ImageFile> getImagFiles() {
        return new HashSet<>(imageFileRepository.findAll());
    }

    public long countFile() {
        return imageFileRepository.countWith();
    }

    public MediaType getMediaType(String contentType) {
        return switch (contentType) {
            case MediaType.IMAGE_PNG_VALUE -> MediaType.IMAGE_PNG;
            case MediaType.IMAGE_GIF_VALUE -> MediaType.IMAGE_GIF;
            default -> MediaType.IMAGE_JPEG;
        };
    }

    public ImageFile findImageById(Long id) {
        return imageFileRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(String.format(ErrorMessages.IMAGE_FILE_NOT_FOUND,id)));
    }
}