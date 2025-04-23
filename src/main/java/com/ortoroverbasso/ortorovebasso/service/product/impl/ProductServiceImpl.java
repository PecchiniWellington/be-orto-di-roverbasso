package com.ortoroverbasso.ortorovebasso.service.product.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.GenericResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_pricing.ProductPricingRequestDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_informations.ProductInformationEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_large_quantities_price.ProductLargeQuantityPriceEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_pricing.ProductPricingEntity;
import com.ortoroverbasso.ortorovebasso.exception.ProductNotFoundException;
import com.ortoroverbasso.ortorovebasso.mapper.product.ProductMapper;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_information.ProductInformationMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.product_attributes.ProductAttributesRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.product_information.ProductInformationRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.product_large_quantity_price.ProductLargeQuantityPriceRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.product_pricing_repository.ProductPricingRepository;
import com.ortoroverbasso.ortorovebasso.repository.tags.ProductTagsRepository;
import com.ortoroverbasso.ortorovebasso.service.product.IProductService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductInformationRepository productInformationRepository;
    @Autowired
    private ProductPricingRepository productPricingInfoRepository;
    @Autowired
    private ProductLargeQuantityPriceRepository productLargeQuantityPriceRepository;
    @Autowired
    private ProductTagsRepository productTagsRepository;
    @Autowired
    private ProductAttributesRepository productAttributesRepository;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto dto) {

        ProductEntity product = ProductMapper.toEntity(dto);

        if (dto.getPriceLargeQuantities() == null) {
            product.setPriceLargeQuantities(new ArrayList<>());
        } else {
            List<Long> ids = dto.getPriceLargeQuantities().stream()
                    .map(ProductLargeQuantityPriceRequestDto::getId)
                    .collect(Collectors.toList());

            List<ProductLargeQuantityPriceEntity> prices = productLargeQuantityPriceRepository.findAllById(ids);

            if (prices.size() != ids.size()) {
                throw new EntityNotFoundException("Some ProductLargeQuantityPriceEntity not found");
            }

            product.setPriceLargeQuantities(prices);
        }

        product = productRepository.save(product);

        return ProductMapper.toResponseDto(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        return ProductMapper.toResponseListDto(products);
    }

    @Override
    public ProductResponseDto getProductById(Long productId) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        boolean hasTags = productTagsRepository.existsByProductId(productId);
        boolean hasAttributes = productAttributesRepository.existsByProductId(productId);

        ProductResponseDto p = ProductMapper.toResponseDto(product);

        p.setTags(hasTags);
        p.setAttributes(hasAttributes);

        return p;
    }

    @Override
    public ProductResponseDto createProductPriceInfo(Long productId, ProductPricingRequestDto productPriceInfo) {
        try {
            ProductEntity product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(productId));

            ProductPricingEntity productPricingInfoEntity = new ProductPricingEntity();
            productPricingInfoEntity.setSku(product.getSku());
            productPricingInfoEntity.setWholesalePrice(productPriceInfo.getWholesalePrice());
            productPricingInfoEntity.setRetailPrice(productPriceInfo.getRetailPrice());
            productPricingInfoEntity.setInShopsPrice(productPriceInfo.getInShopsPrice());

            productPricingInfoRepository.save(productPricingInfoEntity);

            List<ProductLargeQuantityPriceResponseDto> priceDtos = product.getPriceLargeQuantities().stream()
                    .map(priceEntity -> new ProductLargeQuantityPriceResponseDto(
                            priceEntity.getId(),
                            priceEntity.getQuantity(),
                            priceEntity.getPrice()))
                    .collect(Collectors.toList());

            Long manufacturerId = product.getManufacturer() != null ? product.getManufacturer().getId() : null;

            return new ProductResponseDto(
                    product.getId(),
                    product.getSku(),
                    product.getRetailPrice(),
                    product.getCategory(),
                    product.getWeight(),
                    product.getActive(),
                    productPricingInfoEntity.getWholesalePrice(),
                    productPricingInfoEntity.getInShopsPrice(),
                    product.getTags(),
                    manufacturerId,
                    priceDtos,
                    product.getAttributes()

            );

        } catch (ProductNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating product pricing info.", e);
        }
    }

    @Override
    public List<ProductInformationResponseDto> getProductPrices(Long productId) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        ProductInformationEntity productInformationPrice = productInformationRepository.findBySku(product.getSku());

        if (productInformationPrice != null) {
            return Collections.singletonList(ProductInformationMapper.toResponseDto(productInformationPrice));

        } else {
            return Collections.emptyList();
        }

    }

    @Override
    public GenericResponseDto deleteProduct(Long productId) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        // Elimina le informazioni di prodotto associate
        productInformationRepository.deleteById(product.getId());

        // Elimina il prodotto
        productRepository.delete(product);

        return new GenericResponseDto(200, "Prodotto eliminato con successo. ID: " + productId);
    }

}
