package com.ortoroverbasso.ortorovebasso.service.images;

import com.ortoroverbasso.ortorovebasso.dto.images.ImagesResponseDto;

public interface IImagesService {
    // Define the methods that will be implemented in the service class
    ImagesResponseDto uploadImage(String imagePath);

    byte[] downloadImage(String imageId);

    void deleteImage(Long imageId);

    // Add any other methods related to image processing or management

}
