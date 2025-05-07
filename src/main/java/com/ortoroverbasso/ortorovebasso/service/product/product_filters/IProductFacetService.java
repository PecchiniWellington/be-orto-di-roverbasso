package com.ortoroverbasso.ortorovebasso.service.product.product_filters;

import org.springframework.data.domain.Pageable;

import com.ortoroverbasso.ortorovebasso.dto.filters.paginate.PaginatedResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.ProductFacetResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.ProductFilterRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;

public interface IProductFacetService {
    ProductFacetResponseDto getFacets(ProductFilterRequestDto filterDto);

    PaginatedResponseDto<ProductResponseDto> getFilteredProducts(
            ProductFilterRequestDto filterDto,
            Pageable pageable);
}
