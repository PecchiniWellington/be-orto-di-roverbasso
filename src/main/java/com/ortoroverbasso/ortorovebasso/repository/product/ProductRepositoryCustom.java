package com.ortoroverbasso.ortorovebasso.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.ProductFilterRequestDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;

public interface ProductRepositoryCustom {
    Page<ProductEntity> findFilteredProducts(ProductFilterRequestDto filter, Pageable pageable);
}
