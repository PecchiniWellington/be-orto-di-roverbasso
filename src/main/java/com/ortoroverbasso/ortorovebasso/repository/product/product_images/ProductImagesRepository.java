package com.ortoroverbasso.ortorovebasso.repository.product.product_images;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ortoroverbasso.ortorovebasso.entity.product.product_images.ProductImageEntity;

public interface ProductImagesRepository extends JpaRepository<ProductImageEntity, Long> {
    List<ProductImageEntity> findByProductId(Long productId);

}
