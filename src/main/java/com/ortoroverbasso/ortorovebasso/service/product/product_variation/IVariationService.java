package com.ortoroverbasso.ortorovebasso.service.product.product_variation;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.product.product_variation.VariationRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_variation.VariationResponseDto;

public interface IVariationService {

    VariationResponseDto createVariation(VariationRequestDto variationRequestDto);

    VariationResponseDto getVariationById(Long id);

    List<VariationResponseDto> getAllVariations();

    void deleteVariation(Long id);
}
