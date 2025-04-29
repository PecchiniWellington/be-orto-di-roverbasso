package com.ortoroverbasso.ortorovebasso.service.product.product_large_quantity_price.impl;

import java.util.List;

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
public class ProductLargeQuantityPriceServiceImpl implements IProductLargeQuantityPriceService {

    private final IProductService productService;
    private final ProductLargeQuantityPriceRepository priceLargeQuantityRepository;

    public ProductLargeQuantityPriceServiceImpl(
            ProductLargeQuantityPriceRepository priceLargeQuantityRepository,
            IProductService productService) {
        this.productService = productService;
        this.priceLargeQuantityRepository = priceLargeQuantityRepository;
    }

    @Validated
    @Override
    public ProductLargeQuantityPriceResponseDto createProductPriceLargeQuantity(
            Long productId,
            @Valid ProductLargeQuantityPriceRequestDto priceLargeQuantityRequestDto) {

        ProductResponseDto product = productService.getProductById(productId);

        if (product == null) {
            throw new ProductNotFoundException(productId);
        }

        ProductEntity p = ProductMapper.fromResponseToEntity(product);

        ProductLargeQuantityPriceEntity priceLargeQuantityEntity = ProductLargeQuantityPriceMapper
                .toEntity(priceLargeQuantityRequestDto);

        priceLargeQuantityEntity.setProduct(p);

        ProductLargeQuantityPriceEntity savedPrice = priceLargeQuantityRepository.save(priceLargeQuantityEntity);

        return ProductLargeQuantityPriceMapper.toResponseDto(savedPrice);
    }

    @Override
    public List<ProductLargeQuantityPriceResponseDto> getProductLargeQuantityPriceByProductId(Long productId) {
        ProductResponseDto product = productService.getProductById(productId);
        ProductEntity p = ProductMapper.fromResponseToEntity(product);

        List<ProductLargeQuantityPriceEntity> prices = priceLargeQuantityRepository.findByProduct(p);

        if (prices.isEmpty()) {
            return List.of();
        }

        return prices.stream()
                .map(ProductLargeQuantityPriceMapper::toResponseDto)
                .toList();
    }

    @Override
    public ProductLargeQuantityPriceResponseDto updateProductPriceLargeQuantity(
            Long productId,
            Long priceId,
            ProductLargeQuantityPriceRequestDto productPriceLargeQuantityRequestDto) {

        ProductLargeQuantityPriceEntity existingPrice = priceLargeQuantityRepository
                .findByIdAndProductId(priceId, productId);

        existingPrice.setQuantity(productPriceLargeQuantityRequestDto.getQuantity());
        existingPrice.setPrice(productPriceLargeQuantityRequestDto.getPrice());

        ProductLargeQuantityPriceEntity savedPrice = priceLargeQuantityRepository.save(existingPrice);

        return ProductLargeQuantityPriceMapper.toResponseDto(savedPrice);
    }

    @Override
    public String deleteProductPriceLargeQuantity(Long priceId) {

        ProductLargeQuantityPriceEntity existingPrice = priceLargeQuantityRepository
                .findById(priceId)
                .orElseThrow(() -> new ProductNotFoundException(priceId));

        priceLargeQuantityRepository.delete(existingPrice);

        return "Product price large quantity with ID " + priceId
                + " deleted successfully.";
    }

}
