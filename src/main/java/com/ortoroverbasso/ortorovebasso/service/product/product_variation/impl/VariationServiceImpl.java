package com.ortoroverbasso.ortorovebasso.service.product.product_variation.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.product.product_variation.VariationRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_variation.VariationResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.product_variation.VariationEntity;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_variation.VariationMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.product_variation.VariationRepository;
import com.ortoroverbasso.ortorovebasso.service.product.product_variation.IVariationService;

@Service
public class VariationServiceImpl implements IVariationService {

    @Autowired
    private VariationRepository variationRepository;

    @Override
    public VariationResponseDto getVariationById(Long id) {
        VariationEntity variationEntity = variationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Variation not found"));
        return VariationMapper.toResponse(variationEntity);
    }

    @Override
    public List<VariationResponseDto> getAllVariations() {
        List<VariationEntity> variationEntities = variationRepository.findAll();
        return variationEntities.stream()
                .map(VariationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public VariationResponseDto createVariation(VariationRequestDto variationRequestDto) {
        VariationEntity variationEntity = VariationMapper.toEntity(variationRequestDto);
        variationEntity = variationRepository.save(variationEntity);
        return VariationMapper.toResponse(variationEntity);
    }

    @Override
    public void deleteVariation(Long id) {
        variationRepository.deleteById(id);
    }
}
