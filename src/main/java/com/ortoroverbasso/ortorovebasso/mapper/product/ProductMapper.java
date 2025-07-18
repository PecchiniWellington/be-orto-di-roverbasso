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
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_why_choose.ProductWhyChooseResponseNoProductIdDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_information.ProductInformationMapper;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_why_choose.ProductWhyChooseMapper;

/**
 * Mapper per la conversione tra ProductEntity e DTO
 */
@Component
public class ProductMapper {

        /**
         * Converte un ProductRequestDto in ProductEntity
         */
        public static ProductEntity toEntity(ProductRequestDto dto) {
                if (dto == null) {
                        return null;
                }

                ProductEntity product = new ProductEntity();
                mapBasicFields(dto, product);
                return product;
        }

        /**
         * Converte una lista di ProductEntity in lista di ProductResponseDto
         */
        public static List<ProductResponseDto> toResponseDtoList(List<ProductEntity> products) {
                if (products == null || products.isEmpty()) {
                        return Collections.emptyList();
                }

                return products.stream()
                                .map(ProductMapper::toResponseDto)
                                .collect(Collectors.toList());
        }

        /**
         * Converte un ProductEntity in ProductResponseDto con tutti i dettagli
         */
        public static ProductResponseDto toResponseDto(ProductEntity product) {
                if (product == null) {
                        return null;
                }

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

        /**
         * Converte un ProductResponseDto in ProductEntity (per aggiornamenti)
         */
        public static ProductEntity fromResponseToEntity(ProductResponseDto dto) {
                if (dto == null) {
                        return null;
                }

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

        /**
         * Aggiorna un ProductEntity esistente con i dati da ProductRequestDto
         */
        public static void updateEntityFromDto(ProductRequestDto dto, ProductEntity entity) {
                if (dto == null || entity == null) {
                        return;
                }

                // Aggiorna solo i campi non null del DTO
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

        /**
         * Metodo utility per creare un ProductResponseDto semplificato (per liste)
         */
        public static ProductResponseDto toSimpleResponseDto(ProductEntity product) {
                if (product == null) {
                        return null;
                }

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

        /**
         * Metodo utility per convertire singolo ProductLargeQuantityPriceEntity
         */
        public static ProductLargeQuantityPriceResponseDto toLargeQuantityDto(
                        com.ortoroverbasso.ortorovebasso.entity.product.product_large_quantities_price.ProductLargeQuantityPriceEntity entity) {
                if (entity == null) {
                        return null;
                }

                return new ProductLargeQuantityPriceResponseDto(
                                entity.getId(),
                                entity.getQuantity(),
                                entity.getPrice());
        }

        // Metodi helper privati

        private static void mapBasicFields(ProductRequestDto dto, ProductEntity product) {
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

        private static Long extractManufacturerId(ProductEntity product) {
                return Optional.ofNullable(product.getManufacturer())
                                .map(manufacturer -> manufacturer.getId())
                                .orElse(null);
        }

        private static String extractProductName(ProductEntity product) {
                return Optional.ofNullable(product.getProductInformation())
                                .map(info -> info.getName())
                                .orElse(null);
        }

        private static Long extractCategoryId(ProductEntity product) {
                return Optional.ofNullable(product.getCategory())
                                .map(category -> category.getId())
                                .orElse(null);
        }

        private static List<ProductLargeQuantityPriceResponseDto> mapPriceLargeQuantities(ProductEntity product) {
                if (product.getPriceLargeQuantities() == null || product.getPriceLargeQuantities().isEmpty()) {
                        return Collections.emptyList();
                }

                return product.getPriceLargeQuantities().stream()
                                .map(priceEntity -> new ProductLargeQuantityPriceResponseDto(
                                                priceEntity.getId(),
                                                priceEntity.getQuantity(),
                                                priceEntity.getPrice()))
                                .collect(Collectors.toList());
        }

        private static List<ProductImagesShortDto> mapProductImages(ProductEntity product) {
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

        private static com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationResponseDto mapProductInformation(
                        ProductEntity product) {
                return Optional.ofNullable(product.getProductInformation())
                                .map(ProductInformationMapper::toResponseDto)
                                .orElse(null);
        }

        private static List<ProductFeaturesResponseDto> mapProductFeatures(ProductEntity product) {
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

        private static List<ProductWhyChooseResponseNoProductIdDto> mapWhyChoose(ProductEntity product) {
                if (product.getWhyChoose() == null || product.getWhyChoose().isEmpty()) {
                        return Collections.emptyList();
                }

                return product.getWhyChoose().stream()
                                .map(ProductWhyChooseMapper::toResponseWithoutProductId)
                                .collect(Collectors.toList());
        }
}
