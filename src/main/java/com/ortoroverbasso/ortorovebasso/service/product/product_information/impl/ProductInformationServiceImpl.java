package com.ortoroverbasso.ortorovebasso.service.product.product_information.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.product_informations.ProductInformationEntity;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_information.ProductInformationMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.product_information.ProductInformationRepository;
import com.ortoroverbasso.ortorovebasso.service.product.product_information.IProductInformationService;

@Service
public class ProductInformationServiceImpl implements IProductInformationService {
    private final ProductInformationRepository productInformationRepository;

    public ProductInformationServiceImpl(ProductInformationRepository productInformationRepository) {
        this.productInformationRepository = productInformationRepository;
    }

    @Override
    public ProductInformationResponseDto getProductInformationById(Long id) {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProductInformationResponseDto createProductInformation(
            ProductInformationRequestDto productInformationRequestDto) {
        ProductInformationEntity productInformationEntity = ProductInformationMapper
                .toEntity(productInformationRequestDto);
        productInformationEntity = productInformationRepository.save(productInformationEntity);
        return ProductInformationMapper.toResponseDto(productInformationEntity);
    }

    @Override
    public List<ProductInformationResponseDto> getAllProductInformation() {
        List<ProductInformationEntity> productInformationEntities = productInformationRepository.findAll();
        return productInformationEntities.stream()
                .map(ProductInformationMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<ProductInformationResponseDto> getProductInformationByProductId(Long productId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProductInformationResponseDto updateProductInformation(Long id,
            ProductInformationResponseDto productInformation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteProductInformation(Long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public ProductInformationResponseDto getProductInformationBySku(String sku) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductInformationBySku'");
    }

}
