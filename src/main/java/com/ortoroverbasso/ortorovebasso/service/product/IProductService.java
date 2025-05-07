package com.ortoroverbasso.ortorovebasso.service.product;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ortoroverbasso.ortorovebasso.dto.GenericResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.filters.paginate.PaginatedResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.ProductFacetResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.ProductFilterRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;

public interface IProductService {

    ProductResponseDto createProduct(ProductRequestDto product);

    ProductResponseDto getProductById(Long productId);

    ProductResponseDto updateProduct(Long productId, ProductRequestDto product);

    List<ProductResponseDto> getAllProducts();

    GenericResponseDto deleteProduct(Long productId);

    List<ProductResponseDto> getProductsByCategory(Long subCategoryId);

    List<ProductResponseDto> getProductsByCategorySlug(String categorySlug);

    PaginatedResponseDto<ProductResponseDto> getFilteredProducts(ProductFilterRequestDto filterDto, Pageable pageable);

    ProductFacetResponseDto getAvailableFilters(ProductFilterRequestDto filterDto);

}
