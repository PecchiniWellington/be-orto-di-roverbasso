package com.ortoroverbasso.ortorovebasso.controller.images;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/upload/multiple")
    public ResponseEntity<List<ImagesDetailDto>> uploadMultipleImages(
            @RequestParam("files") List<MultipartFile> files) {
        return ResponseEntity.ok(imagesService.uploadImages(files));
    }

    // DELETE singola (già presente)
    @DeleteMapping("/{imageId}/delete")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        imagesService.deleteImage(imageId);
        return ResponseEntity.noContent().build();
    }

    // DELETE multipla
    @DeleteMapping("/delete/multiple")
    public ResponseEntity<Void> deleteMultipleImages(@RequestBody List<Long> imageIds) {
        imagesService.deleteImages(imageIds);
        return ResponseEntity.noContent().build();
    }
}
