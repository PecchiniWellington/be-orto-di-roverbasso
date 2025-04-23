package com.ortoroverbasso.ortorovebasso.repository.product.product_variation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.product.product_variation.ProductVariationEntity;

@Repository
public interface ProductVariationRepository extends JpaRepository<ProductVariationEntity, Long> {
    // Custom query methods can be defined here if needed
    List<ProductVariationEntity> findAllByProductId(Long productId);

    ProductVariationEntity findByProductIdAndId(Long productId, Long variationId);

}
