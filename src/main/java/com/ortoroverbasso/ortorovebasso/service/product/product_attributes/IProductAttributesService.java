package com.ortoroverbasso.ortorovebasso.service.product.product_attributes;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.product.product_attributes.ProductAttributesRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_attributes.ProductAttributesResponseDto;

public interface IProductAttributesService {

        List<ProductAttributesResponseDto> getAllProductAttributesByProductId(Long productId);

        ProductAttributesResponseDto createProductAttribute(
                        Long productId,
                        ProductAttributesRequestDto productAttributesRequestDto);

        void deleteProductAttributes(
                        Long productId,
                        Long attributesId);

        ProductAttributesResponseDto getProductAttributesById(
                        Long productId,
                        Long attributesId);

        ProductAttributesResponseDto updateProductAttributes(
                        Long productId,
                        Long attributesId,
                        ProductAttributesRequestDto productAttributesRequestDto);
}
