package com.ortoroverbasso.ortorovebasso.service.product.product_attributes;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.product.product_attributes.ProductAttributesRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_attributes.ProductAttributesResponseDto;

public interface IProductAttributesService {

    List<ProductAttributesResponseDto> getProductAttributesByProductId(Long productId);

    ProductAttributesResponseDto saveProductAttributes(ProductAttributesRequestDto productAttributesRequestDto);

    void deleteProductAttributes(Long id);

    ProductAttributesResponseDto getProductAttributesById(Long id);

    List<ProductAttributesResponseDto> getAllProductAttributes();
}
