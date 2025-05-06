package com.ortoroverbasso.ortorovebasso.mapper.product;

import java.util.List;
import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_features.ProductFeaturesResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_images.ProductImagesShortDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_why_choose.ProductWhyChooseResponseNoProductIdDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_large_quantities_price.ProductLargeQuantityPriceEntity;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_information.ProductInformationMapper;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_why_choose.ProductWhyChooseMapper;

public class ProductMapper {

        public static ProductEntity toEntity(ProductRequestDto dto) {
                ProductEntity product = new ProductEntity();
                product.setSku(dto.getSku());
                product.setRetailPrice(dto.getRetailPrice());
                product.setActive(dto.getActive());
                product.setDiscount(dto.getDiscount());

                // Mappare priceLargeQuantities se esistono nel DTO
                if (dto.getPriceLargeQuantities() != null) {
                        List<ProductLargeQuantityPriceEntity> priceEntities = dto.getPriceLargeQuantities().stream()
                                        .map(priceDto -> new ProductLargeQuantityPriceEntity(priceDto.getQuantity(),
                                                        priceDto.getPrice()))
                                        .collect(Collectors.toList());
                        product.setPriceLargeQuantities(priceEntities);
                }

                return product;
        }

        public static List<ProductResponseDto> toResponseListDto(List<ProductEntity> products) {
                return products.stream()
                                .map(ProductMapper::toResponseDto)
                                .collect(Collectors.toList());
        }

        public static ProductResponseDto toResponseDto(ProductEntity product) {

                List<ProductLargeQuantityPriceResponseDto> priceDtos = product.getPriceLargeQuantities().stream()
                                .map(priceEntity -> new ProductLargeQuantityPriceResponseDto(
                                                priceEntity.getId(),
                                                priceEntity.getQuantity(),
                                                priceEntity.getPrice()))
                                .collect(Collectors.toList());

                Long manufacturerId = product.getManufacturer() != null ? product.getManufacturer().getId() : null;

                List<ProductImagesShortDto> productImagesDtos = product.getProductImages().stream()
                                .map(image -> new ProductImagesShortDto(
                                                image.getId(),
                                                image.getUrl(),
                                                image.isCover()))
                                .collect(Collectors.toList());

                List<ProductFeaturesResponseDto> productFeaturesDto = product.getProductFeatures().stream()
                                .map(feature -> new ProductFeaturesResponseDto(
                                                feature.getId(),
                                                feature.getFeature(),
                                                feature.getValue()))
                                .collect(Collectors.toList());

                ProductInformationResponseDto productInformationResponseDto = product.getProductInformation() != null
                                ? ProductInformationMapper.toResponseDto(product.getProductInformation())
                                : null;

                List<ProductWhyChooseResponseNoProductIdDto> whyChooseResponses = product.getWhyChoose().stream()
                                .map(ProductWhyChooseMapper::toResponseWithoutProductId)
                                .collect(Collectors.toList());

                ProductResponseDto productResponseDto = new ProductResponseDto();
                productResponseDto.setId(product.getId());
                productResponseDto.setSku(product.getSku());
                productResponseDto.setRetailPrice(product.getRetailPrice());
                productResponseDto.setActive(product.getActive());
                productResponseDto.setWholesalePrice(product.getWholesalePrice());
                productResponseDto.setInShopsPrice(product.getInShopsPrice());
                productResponseDto.setTags(product.getTags());
                productResponseDto.setManufacturer(manufacturerId);
                productResponseDto.setPriceLargeQuantities(priceDtos);
                productResponseDto.setAttributes(product.getAttributes());
                productResponseDto.setProductImages(productImagesDtos);
                productResponseDto.setProductInformation(productInformationResponseDto);
                productResponseDto.setDiscount(product.getDiscount());
                productResponseDto.setProductFeatures(productFeaturesDto);
                productResponseDto.setWhyChoose(whyChooseResponses);

                return productResponseDto;
        }

        public static ProductEntity fromResponseToEntity(ProductResponseDto dto) {
                ProductEntity product = new ProductEntity();
                product.setId(dto.getId());
                product.setSku(dto.getSku());
                product.setRetailPrice(dto.getRetailPrice());
                product.setActive(dto.getActive());

                // Mappare priceLargeQuantities se esistono nel DTO
                if (dto.getPriceLargeQuantities() != null) {
                        List<ProductLargeQuantityPriceEntity> priceEntities = dto.getPriceLargeQuantities().stream()
                                        .map(priceDto -> new ProductLargeQuantityPriceEntity(priceDto.getQuantity(),
                                                        priceDto.getPrice()))
                                        .collect(Collectors.toList());
                        product.setPriceLargeQuantities(priceEntities);
                }

                return product;
        }

        public static ProductRequestDto fromProductEntityToRequestDto(ProductEntity product) {
                List<ProductLargeQuantityPriceRequestDto> priceLargeQuantities = product.getPriceLargeQuantities()
                                .stream()
                                .map(priceEntity -> new ProductLargeQuantityPriceRequestDto(
                                                priceEntity.getId(),
                                                priceEntity.getQuantity(),
                                                priceEntity.getPrice()))
                                .collect(Collectors.toList());

                ProductRequestDto productRequestDto = new ProductRequestDto();
                productRequestDto.setId(product.getId());
                productRequestDto.setSku(product.getSku());
                productRequestDto.setRetailPrice(product.getRetailPrice());
                productRequestDto.setActive(product.getActive());
                productRequestDto.setWholesalePrice(product.getWholesalePrice());
                productRequestDto.setInShopsPrice(product.getInShopsPrice());
                productRequestDto.setPriceLargeQuantities(priceLargeQuantities);
                productRequestDto.setDiscount(product.getDiscount());

                return productRequestDto;
        }

}
