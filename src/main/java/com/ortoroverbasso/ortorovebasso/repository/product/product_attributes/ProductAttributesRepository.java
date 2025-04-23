package com.ortoroverbasso.ortorovebasso.repository.product.product_attributes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.product.product_attributes.ProductAttributesEntity;

@Repository
public interface ProductAttributesRepository extends JpaRepository<ProductAttributesEntity, Long> {
    List<ProductAttributesEntity> findAllByProductId(Long productId);

    ProductAttributesEntity findByProductIdAndId(Long productId, Long attributeId);

    boolean existsByProductId(Long productId);

}
