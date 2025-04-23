package com.ortoroverbasso.ortorovebasso.service.product.product_variation;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.product.product_variation.ProductVariationRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_variation.ProductVariationResponseDto;

public interface IProductVariationService {

    ProductVariationResponseDto createProductVariation(
            Long productId,
            ProductVariationRequestDto variationRequestDto);

    ProductVariationResponseDto getProductVariationById(
            Long productId,
            Long variationId);

    List<ProductVariationResponseDto> getAllProductVariations(Long productId);

    ProductVariationResponseDto updateProductVariation(
            Long productId,
            Long variationId,
            ProductVariationRequestDto variationRequestDto);

    void deleteVariation(Long id);
}
