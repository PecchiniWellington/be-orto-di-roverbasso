package com.ortoroverbasso.ortorovebasso.controller.images;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ortoroverbasso.ortorovebasso.dto.images.ImagesDetailDto;
import com.ortoroverbasso.ortorovebasso.service.images.IImagesDetailService;

@RestController
@RequestMapping("/api/image")
public class ImagesController {

    @Autowired
    public IImagesDetailService imagesService;

    @PostMapping("/upload")
    public ResponseEntity<ImagesDetailDto> uploadGenericImage(
            @RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(imagesService.uploadImage(file));
    }

    @DeleteMapping("/{imageId}/delete")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        imagesService.deleteImage(imageId);
        return ResponseEntity.noContent().build();
    }

}
