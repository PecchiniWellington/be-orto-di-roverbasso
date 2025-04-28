package com.ortoroverbasso.ortorovebasso.controller.product.product_images;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ortoroverbasso.ortorovebasso.dto.images.ImageConnectRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.images.ImageConnectResponse;
import com.ortoroverbasso.ortorovebasso.dto.product.product_images.ProductImagesResponseDto;
import com.ortoroverbasso.ortorovebasso.service.images.IImagesDetailService;
import com.ortoroverbasso.ortorovebasso.service.product.product_images.IProductImagesService;

@RestController
@RequestMapping("/api/products")
public class ProductImagesController {

    @Autowired
    private IProductImagesService productImageService;

    @Autowired
    private IImagesDetailService imagesService;

    @PostMapping("/{productId}/image/upload")
    public ResponseEntity<List<ProductImagesResponseDto>> uploadImage(
            @PathVariable Long productId,
            @RequestPart("file") MultipartFile file) {
        // Fase 1: Carica l'immagine
        List<ProductImagesResponseDto> productImageUploaded = productImageService.uploadProductImage(productId, file);

        return ResponseEntity.ok(productImageUploaded); // Restituisci i dettagli dell'immagine
    }

    // Step 2: Associa l'immagine al prodotto
    /*
     * @PostMapping("/{productId}/image/uploadDetails")
     * public ResponseEntity<ProductImagesResponseDto> uploadProductImageDetails(
     *
     * @PathVariable Long productId,
     *
     * @RequestBody ImagesDetailDto requestDto) {
     *
     * return ResponseEntity.ok(productImageService.uploadProductImage(productId,
     * requestDto));
     * }
     */
    @GetMapping("/{productId}/image/all")
    public ResponseEntity<List<ProductImagesResponseDto>> getImagesByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productImageService.getImagesByProductId(productId));
    }

    @DeleteMapping("/{productId}/image/delete")
    public ResponseEntity<List<ProductImagesResponseDto>> deleteProductImages(
            @PathVariable Long productId,
            @RequestBody List<Long> imageIds) {
        return ResponseEntity.ok(productImageService.deleteProductImages(productId, imageIds));
    }

    @PostMapping("/image/connect")
    public ResponseEntity<List<ProductImagesResponseDto>> connectImageToProduct(
            @RequestBody ImageConnectRequestDto imageConnectRequest) {

        return ResponseEntity.ok(imagesService.connectImageToProduct(imageConnectRequest));
    }

    @PostMapping("/image/disconnect")
    public ResponseEntity<ImageConnectResponse> disconnectImagesFromProduct(
            @RequestBody ImageConnectRequestDto imageConnectRequest) {

        return ResponseEntity.ok(imagesService.disconnectImagesFromProduct(imageConnectRequest));
    }
}
