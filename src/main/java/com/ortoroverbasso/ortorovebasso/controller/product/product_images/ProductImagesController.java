package com.ortoroverbasso.ortorovebasso.controller.product.product_images;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ortoroverbasso.ortorovebasso.dto.images.ImagesDetailDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_images.ProductImagesResponseDto;
import com.ortoroverbasso.ortorovebasso.service.images.IImagesDetailService;
import com.ortoroverbasso.ortorovebasso.service.product.product_images.IProductImagesService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api")
public class ProductImagesController {

    @Autowired
    private IProductImagesService productImageService;

    @Autowired
    private IImagesDetailService imagesService;

    @PostMapping("/products/{productId}/image/upload")
    public ResponseEntity<ProductImagesResponseDto> uploadProductImage(
            @PathVariable Long productId,
            @RequestParam("file") MultipartFile file,
            @RequestBody ImagesDetailDto requestDto) {
        return ResponseEntity.ok(productImageService.uploadProductImage(productId, file, requestDto));
    }

    @GetMapping("/products/{productId}/images")
    public ResponseEntity<List<ProductImagesResponseDto>> getImagesByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productImageService.getImagesByProductId(productId));
    }
}
