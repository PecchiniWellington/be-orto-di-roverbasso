package com.ortoroverbasso.ortorovebasso.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.product.ProductPricingInfoEntity;

@Repository
public interface ProductPricingInfoRepository extends JpaRepository<ProductPricingInfoEntity, Long> {

    ProductPricingInfoEntity findBySku(String sku);

}
