package com.ortoroverbasso.ortorovebasso.service.product.product_large_quantity_price.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_large_quantities_price.ProductLargeQuantityPriceEntity;
import com.ortoroverbasso.ortorovebasso.exception.ProductNotFoundException;
import com.ortoroverbasso.ortorovebasso.mapper.product.ProductMapper;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_large_quantities_price.ProductLargeQuantityPriceMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.product_large_quantity_price.ProductLargeQuantityPriceRepository;
import com.ortoroverbasso.ortorovebasso.service.product.IProductService;
import com.ortoroverbasso.ortorovebasso.service.product.product_large_quantity_price.IProductLargeQuantityPriceService;

import jakarta.validation.Valid;

@Service
@Validated
public class ProductLargeQuantityPriceServiceImpl implements IProductLargeQuantityPriceService {

    private final IProductService productService;
    private final ProductLargeQuantityPriceRepository priceLargeQuantityRepository;
    private final ProductMapper productMapper;
    private final ProductLargeQuantityPriceMapper productLargeQuantityPriceMapper;

    public ProductLargeQuantityPriceServiceImpl(
            ProductLargeQuantityPriceRepository priceLargeQuantityRepository,
            IProductService productService,
            ProductMapper productMapper,
            ProductLargeQuantityPriceMapper productLargeQuantityPriceMapper) {
        this.priceLargeQuantityRepository = priceLargeQuantityRepository;
        this.productService = productService;
        this.productMapper = productMapper;
        this.productLargeQuantityPriceMapper = productLargeQuantityPriceMapper;
    }

    @Override
    public ProductLargeQuantityPriceResponseDto createProductPriceLargeQuantity(
            Long productId,
            @Valid ProductLargeQuantityPriceRequestDto requestDto) {

        ProductResponseDto productDto = productService.getProductById(productId);
        if (productDto == null) {
            throw new ProductNotFoundException(productId);
        }

        ProductEntity product = productMapper.fromResponseToEntity(productDto);
        ProductLargeQuantityPriceEntity entity = productLargeQuantityPriceMapper.toEntity(requestDto);
        entity.setProduct(product);

        ProductLargeQuantityPriceEntity saved = priceLargeQuantityRepository.save(entity);

        return productLargeQuantityPriceMapper.toResponseDto(saved);
    }

    @Override
    public List<ProductLargeQuantityPriceResponseDto> getProductLargeQuantityPriceByProductId(Long productId) {
        ProductResponseDto productDto = productService.getProductById(productId);
        if (productDto == null) {
            throw new ProductNotFoundException(productId);
        }

        ProductEntity product = productMapper.fromResponseToEntity(productDto);
        List<ProductLargeQuantityPriceEntity> prices = priceLargeQuantityRepository.findByProduct(product);

        if (prices.isEmpty())
            return Collections.emptyList();

        return prices.stream()
                .map(productLargeQuantityPriceMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductLargeQuantityPriceResponseDto updateProductPriceLargeQuantity(
            Long productId,
            Long priceId,
            ProductLargeQuantityPriceRequestDto requestDto) {

        ProductLargeQuantityPriceEntity existing = priceLargeQuantityRepository
                .findByIdAndProductId(priceId, productId);

        if (existing == null) {
            throw new ProductNotFoundException(priceId);
        }

        existing.setQuantity(requestDto.getQuantity());
        existing.setPrice(requestDto.getPrice());

        ProductLargeQuantityPriceEntity saved = priceLargeQuantityRepository.save(existing);

        return productLargeQuantityPriceMapper.toResponseDto(saved);
    }

    @Override
    public String deleteProductPriceLargeQuantity(Long priceId) {
        ProductLargeQuantityPriceEntity entity = priceLargeQuantityRepository.findById(priceId)
                .orElseThrow(() -> new ProductNotFoundException(priceId));

        priceLargeQuantityRepository.delete(entity);
        return "Product price large quantity with ID " + priceId + " deleted successfully.";
    }
}
