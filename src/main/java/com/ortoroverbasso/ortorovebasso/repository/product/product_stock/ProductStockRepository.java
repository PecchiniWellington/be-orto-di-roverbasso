package com.ortoroverbasso.ortorovebasso.repository.product.product_stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.product.product_stock.ProductStockEntity;

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStockEntity, Long> {
    ProductStockEntity findBySku(String sku);
}
