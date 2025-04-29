package com.ortoroverbasso.ortorovebasso.repository.product.product_features;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.product.product_features.ProductFeaturesEntity;

@Repository
public interface ProductFeaturesRepository extends JpaRepository<ProductFeaturesEntity, Long> {
    ProductFeaturesEntity findByProductId(Long product);

}
