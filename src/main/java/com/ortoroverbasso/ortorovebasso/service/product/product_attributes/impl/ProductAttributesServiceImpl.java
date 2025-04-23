package com.ortoroverbasso.ortorovebasso.service.product.product_attributes.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.product.product_attributes.ProductAttributesRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_attributes.ProductAttributesResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_attributes.ProductAttributesEntity;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_attribute.ProductAttributeMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.product_attributes.ProductAttributesRepository;
import com.ortoroverbasso.ortorovebasso.service.product.product_attributes.IProductAttributesService;

@Service
public class ProductAttributesServiceImpl implements IProductAttributesService {

    @Autowired
    private ProductAttributesRepository productAttributesRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductAttributesResponseDto> getAllProductAttributesByProductId(Long productId) {

        List<ProductAttributesEntity> attributes = productAttributesRepository.findAllByProductId(productId);

        return attributes.stream()
                .map(ProductAttributeMapper::toResponseDto)
                .collect(Collectors.toList());

    }

    @Override
    public ProductAttributesResponseDto createProductAttribute(
            Long productId,
            ProductAttributesRequestDto productAttributesRequestDto) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductAttributesEntity productAttributesEntity = ProductAttributeMapper.toEntity(productAttributesRequestDto);

        productAttributesEntity.setAttributeGroup(productAttributesRequestDto.getAttributeGroup());
        productAttributesEntity.setName(productAttributesRequestDto.getName());
        productAttributesEntity.setIsoCode(productAttributesRequestDto.getIsoCode());

        productAttributesEntity.setProduct(productEntity);

        productAttributesEntity = productAttributesRepository.save(productAttributesEntity);

        return ProductAttributeMapper.toResponseDto(productAttributesEntity);
    }

    @Override
    public void deleteProductAttributes(Long productId, Long attributesId) {
        ProductAttributesEntity productAttributesEntity = productAttributesRepository
                .findByProductIdAndId(productId, attributesId);

        if (productAttributesEntity == null) {
            throw new RuntimeException("Product attribute not found for this product");
        }

        productAttributesRepository.delete(productAttributesEntity);

    }

    @Override
    public ProductAttributesResponseDto getProductAttributesById(Long productId, Long attributesId) {
        ProductAttributesEntity productAttributesEntity = productAttributesRepository
                .findByProductIdAndId(productId, attributesId);
        if (productAttributesEntity == null) {
            throw new RuntimeException("Product attribute not found for this product");
        }
        return ProductAttributeMapper.toResponseDto(productAttributesEntity);

    }

    @Override
    public ProductAttributesResponseDto updateProductAttributes(Long productId, Long attributesId,
            ProductAttributesRequestDto productAttributesRequestDto) {

        ProductAttributesEntity productAttributesEntity = productAttributesRepository
                .findByProductIdAndId(productId, attributesId);
        if (productAttributesEntity == null) {
            throw new RuntimeException("Product attribute not found for this product");
        }
        productAttributesEntity.setAttributeGroup(productAttributesRequestDto.getAttributeGroup());
        productAttributesEntity.setName(productAttributesRequestDto.getName());
        productAttributesEntity.setIsoCode(productAttributesRequestDto.getIsoCode());

        productAttributesEntity = productAttributesRepository.save(productAttributesEntity);

        return ProductAttributeMapper.toResponseDto(productAttributesEntity);

    }

}
