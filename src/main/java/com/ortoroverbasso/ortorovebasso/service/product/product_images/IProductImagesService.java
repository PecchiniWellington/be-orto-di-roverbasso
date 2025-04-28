package com.ortoroverbasso.ortorovebasso.service.product.product_images;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ortoroverbasso.ortorovebasso.dto.product.product_images.ProductImagesResponseDto;

public interface IProductImagesService {
    List<ProductImagesResponseDto> uploadProductImage(
            Long productId,
            MultipartFile file);

    List<ProductImagesResponseDto> getImagesByProductId(Long productId);

    List<ProductImagesResponseDto> deleteProductImages(Long productId, List<Long> imageId);
}
