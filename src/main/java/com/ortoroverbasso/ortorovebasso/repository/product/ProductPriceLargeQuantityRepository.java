package com.ortoroverbasso.ortorovebasso.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.product.ProductPriceLargeQuantityEntity;

@Repository
public interface ProductPriceLargeQuantityRepository extends JpaRepository<ProductPriceLargeQuantityEntity, Long> {

}
