package com.ortoroverbasso.ortorovebasso.service.images;

import org.springframework.web.multipart.MultipartFile;

import com.ortoroverbasso.ortorovebasso.dto.images.ImagesDetailDto;

public interface IImagesDetailService {
    // Define the methods that will be implemented in the service class

    ImagesDetailDto uploadImage(MultipartFile file);

    byte[] downloadImage(String filename);

    void deleteImage(Long imageId);

    // Add any other methods related to image processing or management

}
