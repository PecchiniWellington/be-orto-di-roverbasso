package com.ortoroverbasso.ortorovebasso.service.product.product_information.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_informations.ProductInformationEntity;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_information.ProductInformationMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.product_information.ProductInformationRepository;
import com.ortoroverbasso.ortorovebasso.service.product.product_information.IProductInformationService;

@Service
public class ProductInformationServiceImpl implements IProductInformationService {

    @Autowired
    private ProductInformationRepository productInformationRepository;
    @Autowired
    private ProductRepository productRepository;

    public ProductInformationServiceImpl(ProductInformationRepository productInformationRepository) {
        this.productInformationRepository = productInformationRepository;
    }

    @Override
    public ProductInformationResponseDto getProductInformation(Long productId) {
        ProductInformationEntity productInformationEntity = productInformationRepository.findByProductId(productId);

        return ProductInformationMapper.toResponseDto(productInformationEntity);
    }

    @Override
    public ProductInformationResponseDto createProductInformation(
            Long productId,
            ProductInformationRequestDto productInformationRequestDto) {

        ProductInformationEntity productInformationEntity = ProductInformationMapper
                .toEntity(productInformationRequestDto);
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productInformationEntity.setProduct(productEntity);
        productInformationEntity = productInformationRepository.save(productInformationEntity);
        return ProductInformationMapper.toResponseDto(productInformationEntity);
    }

    @Override
    public List<ProductInformationResponseDto> getProductInformationByProductId(Long productId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProductInformationResponseDto updateProductInformation(Long id,
            ProductInformationRequestDto productInformation) {
        ProductInformationEntity entity = productInformationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Information not found"));
        entity.setName(productInformation.getName());
        entity.setDescription(productInformation.getDescription());
        entity.setSku(productInformation.getSku());
        entity.setUrl(productInformation.getUrl());
        entity.setIsoCode(productInformation.getIsoCode());

        entity.setDateUpdDescription(productInformation.getDateUpdDescription());
        productInformationRepository.save(entity);
        return ProductInformationMapper.toResponseDto(entity);

    }

    @Override
    public void deleteProductInformation(Long id) {
        ProductInformationEntity entity = productInformationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Information not found"));

        productInformationRepository.delete(entity);

    }

    @Override
    public ProductInformationResponseDto getProductInformationBySku(String sku) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductInformationBySku'");
    }

}
