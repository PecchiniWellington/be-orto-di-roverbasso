package com.ortoroverbasso.ortorovebasso.service.product_price_large_quantity.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.product_price_large_quantity.ProductPriceLargeQuantityRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product_price_large_quantity.ProductPriceLargeQuantityResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductPriceLargeQuantityEntity;
import com.ortoroverbasso.ortorovebasso.mapper.product.ProductPriceLargeQuantityMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductPriceLargeQuantityRepository;
import com.ortoroverbasso.ortorovebasso.service.product.IProductService;
import com.ortoroverbasso.ortorovebasso.service.product_price_large_quantity.IProductPriceLargeQuantityService;

@Service
public class ProductPriceLargeQuantityServiceImpl implements IProductPriceLargeQuantityService {

    private final IProductService productService;
    private final ProductPriceLargeQuantityRepository priceLargeQuantityRepository;

    public ProductPriceLargeQuantityServiceImpl(
            ProductPriceLargeQuantityRepository priceLargeQuantityRepository,
            IProductService productService) {
        this.productService = productService;
        this.priceLargeQuantityRepository = priceLargeQuantityRepository;
    }

    @Override
    public ProductPriceLargeQuantityResponseDto createProductPriceLargeQuantity(
            Long productId,
            ProductPriceLargeQuantityRequestDto priceLargeQuantityRequestDto) {

        ProductEntity productEntity = productService.getProductById(productId);

        ProductPriceLargeQuantityEntity priceLargeQuantityEntity = ProductPriceLargeQuantityMapper
                .toEntity(priceLargeQuantityRequestDto);

        priceLargeQuantityEntity.setProduct(productEntity);

        ProductPriceLargeQuantityEntity savedPrice = priceLargeQuantityRepository.save(priceLargeQuantityEntity);

        return ProductPriceLargeQuantityMapper.toResponseDto(savedPrice);
    }

    @Override
    public List<ProductPriceLargeQuantityResponseDto> getProductPricesLargeQuantityByProductId(Long productId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductPricesLargeQuantityByProductId'");
    }

    // Altri metodi per la gestione dei prezzi
}
