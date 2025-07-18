package com.ortoroverbasso.ortorovebasso.mapper.product;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_features.ProductFeaturesResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_images.ProductImagesShortDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_why_choose.ProductWhyChooseResponseNoProductIdDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_large_quantities_price.ProductLargeQuantityPriceEntity;
import com.ortoroverbasso.ortorovebasso.mapper.base.BaseMapper;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_information.ProductInformationMapper;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_why_choose.ProductWhyChooseMapper;

@Component
public class ProductMapper implements BaseMapper<ProductEntity, ProductRequestDto, ProductResponseDto> {

        @Override
        public ProductEntity toEntity(ProductRequestDto dto) {
                if (dto == null)
                        return null;
                ProductEntity product = new ProductEntity();
                mapBasicFields(dto, product);
                return product;
        }

        @Override
        public ProductResponseDto toResponseDto(ProductEntity product) {
                if (product == null)
                        return null;

                return ProductResponseDto.builder()
                                .id(product.getId())
                                .sku(product.getSku())
                                .retailPrice(product.getRetailPrice())
                                .weight(product.getWeight())
                                .active(product.getActive())
                                .wholesalePrice(product.getWholesalePrice())
                                .inShopsPrice(product.getInShopsPrice())
                                .hasTags(product.getHasTags())
                                .manufacturerId(extractManufacturerId(product))
                                .discount(product.getDiscount())
                                .productName(extractProductName(product))
                                .categoryId(extractCategoryId(product))
                                .reference(product.getReference())
                                .quantity(product.getQuantity())
                                .dateAdd(product.getDateAdd())
                                .hasAttributes(product.getHasAttributes())
                                .hasImages(product.getHasImages())
                                .priceLargeQuantities(mapPriceLargeQuantities(product))
                                .productImages(mapProductImages(product))
                                .productInformation(mapProductInformation(product))
                                .productFeatures(mapProductFeatures(product))
                                .whyChoose(mapWhyChoose(product))
                                .build();
        }

        @Override
        public void updateEntityFromDto(ProductRequestDto dto, ProductEntity entity) {
                if (dto == null || entity == null)
                        return;

                Optional.ofNullable(dto.getSku()).ifPresent(entity::setSku);
                Optional.ofNullable(dto.getRetailPrice()).ifPresent(entity::setRetailPrice);
                Optional.ofNullable(dto.getWeight()).ifPresent(entity::setWeight);
                Optional.ofNullable(dto.getActive()).ifPresent(entity::setActive);
                Optional.ofNullable(dto.getWholesalePrice()).ifPresent(entity::setWholesalePrice);
                Optional.ofNullable(dto.getInShopsPrice()).ifPresent(entity::setInShopsPrice);
                Optional.ofNullable(dto.getDiscount()).ifPresent(entity::setDiscount);
                Optional.ofNullable(dto.getReference()).ifPresent(entity::setReference);
                Optional.ofNullable(dto.getQuantity()).ifPresent(entity::setQuantity);
        }

        public ProductEntity fromResponseToEntity(ProductResponseDto dto) {
                if (dto == null)
                        return null;
                ProductEntity product = new ProductEntity();
                product.setId(dto.getId());
                product.setSku(dto.getSku());
                product.setRetailPrice(dto.getRetailPrice());
                product.setWeight(dto.getWeight());
                product.setActive(dto.getActive());
                product.setWholesalePrice(dto.getWholesalePrice());
                product.setInShopsPrice(dto.getInShopsPrice());
                product.setDiscount(dto.getDiscount());
                product.setReference(dto.getReference());
                product.setQuantity(dto.getQuantity());
                return product;
        }

        public ProductResponseDto toSimpleResponseDto(ProductEntity product) {
                if (product == null)
                        return null;

                return ProductResponseDto.builder()
                                .id(product.getId())
                                .sku(product.getSku())
                                .retailPrice(product.getRetailPrice())
                                .active(product.getActive())
                                .productName(extractProductName(product))
                                .categoryId(extractCategoryId(product))
                                .hasImages(product.getHasImages())
                                .hasAttributes(product.getHasAttributes())
                                .build();
        }

        public ProductLargeQuantityPriceResponseDto toLargeQuantityDto(ProductLargeQuantityPriceEntity entity) {
                if (entity == null)
                        return null;
                return new ProductLargeQuantityPriceResponseDto(
                                entity.getId(),
                                entity.getQuantity(),
                                entity.getPrice());
        }

        // Metodi privati di supporto

        private void mapBasicFields(ProductRequestDto dto, ProductEntity product) {
                product.setSku(dto.getSku());
                product.setRetailPrice(dto.getRetailPrice());
                product.setWeight(dto.getWeight());
                product.setActive(dto.getActive());
                product.setWholesalePrice(dto.getWholesalePrice());
                product.setInShopsPrice(dto.getInShopsPrice());
                product.setDiscount(dto.getDiscount());
                product.setReference(dto.getReference());
                product.setQuantity(dto.getQuantity());
        }

        private Long extractManufacturerId(ProductEntity product) {
                return Optional.ofNullable(product.getManufacturer())
                                .map(m -> m.getId())
                                .orElse(null);
        }

        private String extractProductName(ProductEntity product) {
                return Optional.ofNullable(product.getProductInformation())
                                .map(info -> info.getName())
                                .orElse(null);
        }

        private Long extractCategoryId(ProductEntity product) {
                return Optional.ofNullable(product.getCategory())
                                .map(cat -> cat.getId())
                                .orElse(null);
        }

        private List<ProductLargeQuantityPriceResponseDto> mapPriceLargeQuantities(ProductEntity product) {
                if (product.getPriceLargeQuantities() == null || product.getPriceLargeQuantities().isEmpty()) {
                        return Collections.emptyList();
                }

                return product.getPriceLargeQuantities().stream()
                                .map(this::toLargeQuantityDto)
                                .collect(Collectors.toList());
        }

        private List<ProductImagesShortDto> mapProductImages(ProductEntity product) {
                if (product.getProductImages() == null || product.getProductImages().isEmpty()) {
                        return Collections.emptyList();
                }

                return product.getProductImages().stream()
                                .map(image -> new ProductImagesShortDto(
                                                image.getId(),
                                                image.getUrl(),
                                                image.isCover()))
                                .collect(Collectors.toList());
        }

        private ProductInformationResponseDto mapProductInformation(ProductEntity product) {
                return Optional.ofNullable(product.getProductInformation())
                                .map(ProductInformationMapper::toResponseDto)
                                .orElse(null);
        }

        private List<ProductFeaturesResponseDto> mapProductFeatures(ProductEntity product) {
                if (product.getProductFeatures() == null || product.getProductFeatures().isEmpty()) {
                        return Collections.emptyList();
                }

                return product.getProductFeatures().stream()
                                .map(feature -> new ProductFeaturesResponseDto(
                                                feature.getId(),
                                                feature.getFeature(),
                                                feature.getValue()))
                                .collect(Collectors.toList());
        }

        private List<ProductWhyChooseResponseNoProductIdDto> mapWhyChoose(ProductEntity product) {
                if (product.getWhyChoose() == null || product.getWhyChoose().isEmpty()) {
                        return Collections.emptyList();
                }

                return product.getWhyChoose().stream()
                                .map(ProductWhyChooseMapper::toResponseWithoutProductId)
                                .collect(Collectors.toList());
        }
}
