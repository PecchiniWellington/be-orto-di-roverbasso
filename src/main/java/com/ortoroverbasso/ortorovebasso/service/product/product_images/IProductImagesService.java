package com.ortoroverbasso.ortorovebasso.service.product.product_images;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ortoroverbasso.ortorovebasso.dto.images.ImagesDetailDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_images.ProductImagesResponseDto;

public interface IProductImagesService {
    ProductImagesResponseDto uploadProductImage(Long productId, MultipartFile file, ImagesDetailDto request);

    List<ProductImagesResponseDto> getImagesByProductId(Long productId);
}
