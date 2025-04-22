package com.ortoroverbasso.ortorovebasso.service.product.product_large_quantity_price.impl;

import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_large_quantities_price.ProductLargeQuantityPriceEntity;
import com.ortoroverbasso.ortorovebasso.mapper.product.ProductMapper;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_large_quantities_price.ProductLargeQuantityPriceMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.product_large_quantity_price.ProductLargeQuantityPriceRepository;
import com.ortoroverbasso.ortorovebasso.service.product.IProductService;
import com.ortoroverbasso.ortorovebasso.service.product.product_large_quantity_price.IProductLargeQuantityPriceService;

@Service
public class ProductLargeQuantityPriceServiceImpl implements IProductLargeQuantityPriceService {

    private final IProductService productService;
    private final ProductLargeQuantityPriceRepository priceLargeQuantityRepository;

    public ProductLargeQuantityPriceServiceImpl(
            ProductLargeQuantityPriceRepository priceLargeQuantityRepository,
            IProductService productService) {
        this.productService = productService;
        this.priceLargeQuantityRepository = priceLargeQuantityRepository;
    }

    @Override
    public ProductLargeQuantityPriceResponseDto createProductPriceLargeQuantity(
            Long productId,
            ProductLargeQuantityPriceRequestDto priceLargeQuantityRequestDto) {

        ProductResponseDto product = productService.getProductById(productId);
        ProductEntity p = ProductMapper.fromResponseToEntity(product);

        ProductLargeQuantityPriceEntity priceLargeQuantityEntity = ProductLargeQuantityPriceMapper
                .toEntity(priceLargeQuantityRequestDto);

        priceLargeQuantityEntity.setProduct(p);

        ProductLargeQuantityPriceEntity savedPrice = priceLargeQuantityRepository.save(priceLargeQuantityEntity);

        return ProductLargeQuantityPriceMapper.toResponseDto(savedPrice);
    }

}
