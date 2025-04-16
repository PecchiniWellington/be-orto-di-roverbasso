package com.ortoroverbasso.ortorovebasso.repository.product.product_pricing_repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.product.product_pricing.ProductPricingEntity;

@Repository
public interface ProductPricingRepository extends JpaRepository<ProductPricingEntity, Long> {

    ProductPricingEntity findBySku(String sku);

}
