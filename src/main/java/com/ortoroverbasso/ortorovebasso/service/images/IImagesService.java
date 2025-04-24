package com.ortoroverbasso.ortorovebasso.service.images;

import org.springframework.web.multipart.MultipartFile;

import com.ortoroverbasso.ortorovebasso.dto.images.ImagesDetailDto;
import com.ortoroverbasso.ortorovebasso.dto.images.ImagesResponseDto;

public interface IImagesService {
    // Define the methods that will be implemented in the service class

    ImagesResponseDto uploadImage(MultipartFile file, ImagesDetailDto requestDto);

    byte[] downloadImage(String filename);

    // Add any other methods related to image processing or management

}
