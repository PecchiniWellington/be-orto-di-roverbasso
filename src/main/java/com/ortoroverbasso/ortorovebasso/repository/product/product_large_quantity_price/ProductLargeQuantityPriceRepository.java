package com.ortoroverbasso.ortorovebasso.repository.product.product_large_quantity_price;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.product.product_large_quantities_price.ProductLargeQuantityPriceEntity;

@Repository
public interface ProductLargeQuantityPriceRepository extends JpaRepository<ProductLargeQuantityPriceEntity, Long> {
    /* List<ProductPriceLargeQuantityEntity> findBySku(String sku); */

}
