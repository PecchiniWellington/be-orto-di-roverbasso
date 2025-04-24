package com.ortoroverbasso.ortorovebasso.controller.images;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.service.images.IImagesService;

@RestController
@RequestMapping("/api/products/images")
public class ImagesController {

    @Autowired
    public IImagesService imagesService;

    /*
     * @PostMapping()
     * public ImagesResponseDto uploadImage(String imagePath) {
     * return imagesService.uploadImage(imagePath);
     * }
     * 
     * @PostMapping("/download")
     * public byte[] downloadImage(String imageId) {
     * return imagesService.downloadImage(imageId);
     * }
     * 
     * @DeleteMapping("/delete")
     * public void deleteImage(Long imageId) {
     * imagesService.deleteImage(imageId);
     * }
     */

}
