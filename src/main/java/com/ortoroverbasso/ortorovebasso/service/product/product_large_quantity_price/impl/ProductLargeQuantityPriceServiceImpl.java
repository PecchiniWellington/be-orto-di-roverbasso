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
            @Valid ProductLargeQuantityPriceRequestDto priceLargeQuantityRequestDto) {

        // Recupera il prodotto dal servizio
        ProductResponseDto product = productService.getProductById(priceLargeQuantityRequestDto.getProductId());

        // Verifica che il prodotto esista nel database
        if (product == null) {
            throw new ProductNotFoundException(priceLargeQuantityRequestDto.getProductId());
        }

        // Converte il ProductResponseDto in ProductEntity (associato al prodotto)
        ProductEntity p = ProductMapper.fromResponseToEntity(product);

        // Verifica che il prodotto esista nel database
        if (p.getId() == null) {
            // Se il prodotto non esiste (ID nullo), solleva un'eccezione
            throw new ProductNotFoundException(
                    priceLargeQuantityRequestDto.getProductId());
        }

        // Mappa il DTO a ProductLargeQuantityPriceEntity
        ProductLargeQuantityPriceEntity priceLargeQuantityEntity = ProductLargeQuantityPriceMapper
                .toEntity(priceLargeQuantityRequestDto);

        // Associa il prodotto esistente all'entità di prezzo
        priceLargeQuantityEntity.setProduct(p);

        // Salva l'entità di prezzo nel database
        ProductLargeQuantityPriceEntity savedPrice = priceLargeQuantityRepository.save(priceLargeQuantityEntity);

        // Restituisci la risposta dopo aver salvato
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
    public List<ProductLargeQuantityPriceResponseDto> getProductLargeQuantityPrice() {

        List<ProductLargeQuantityPriceEntity> prices = priceLargeQuantityRepository.findAll();

        if (prices.isEmpty()) {
            return List.of();
        }

        return prices.stream()
                .map(ProductLargeQuantityPriceMapper::toResponseDto)
                .toList();
    }

}
