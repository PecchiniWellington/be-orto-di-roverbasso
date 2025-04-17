package com.ortoroverbasso.ortorovebasso.service.product.product_variation;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.product.product_variation.ProductVariationRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_variation.ProductVariationResponseDto;

public interface IProductVariationService {

    ProductVariationResponseDto createVariation(Long productId, ProductVariationRequestDto variationRequestDto);

    ProductVariationResponseDto getProductVariationById(Long id);

    List<ProductVariationResponseDto> getAllProductVariations();

    void deleteVariation(Long id);
}
