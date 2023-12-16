package com.springsecurityjwt.payload.mapper;




import com.springsecurityjwt.entity.ImageFile;
import com.springsecurityjwt.payload.response.ImageFileResponse;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Data
@Component
public class ImageFileMapper {

    public ImageFile mapMultiPartFileToImageFile(MultipartFile file) throws IOException {
        return ImageFile.builder().data(file.getBytes()).contentType(file.getContentType()).isDefault(false).build();
    }

    public ImageFileResponse mapImageFileToImageFileResponse(ImageFile imageFile) {
        return ImageFileResponse.builder()
                .contentType(imageFile.getContentType())
                .imageData(imageFile.getData())
                .build();
    }
}