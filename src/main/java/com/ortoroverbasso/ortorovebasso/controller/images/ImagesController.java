package com.ortoroverbasso.ortorovebasso.controller.images;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.images.ImagesResponseDto;
import com.ortoroverbasso.ortorovebasso.service.images.IImagesService;

@RestController
@RequestMapping("/api/productsImages")
public class ImagesController {

    IImagesService imagesService;

    public ImagesController(IImagesService imagesService) {
        this.imagesService = imagesService;
    }

    @PostMapping
    public ImagesResponseDto uploadImage(String imagePath) {
        return imagesService.uploadImage(imagePath);
    }

    @PostMapping("/download")
    public byte[] downloadImage(String imageId) {
        return imagesService.downloadImage(imageId);
    }

    @DeleteMapping("/delete")
    public void deleteImage(Long imageId) {
        imagesService.deleteImage(imageId);
    }

}
