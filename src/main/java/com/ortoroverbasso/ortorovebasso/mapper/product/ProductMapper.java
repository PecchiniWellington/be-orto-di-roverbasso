package com.ortoroverbasso.ortorovebasso.mapper.product;

import java.util.List;
import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_images.ProductImagesShortDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_large_quantities_price.ProductLargeQuantityPriceEntity;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_information.ProductInformationMapper;

public class ProductMapper {

        public static ProductEntity toEntity(ProductRequestDto dto) {
                ProductEntity product = new ProductEntity();
                product.setSku(dto.getSku());
                product.setWeight(dto.getWeight());
                product.setCategory(dto.getCategory());
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

                ProductInformationResponseDto productInformationResponseDto = product.getProductInformation() != null
                                ? ProductInformationMapper.toResponseDto(product.getProductInformation())
                                : null;

                return new ProductResponseDto(
                                product.getId(),
                                product.getSku(),
                                product.getRetailPrice(),
                                product.getCategory(),
                                product.getWeight(),
                                product.getActive(),
                                product.getWholesalePrice(),
                                product.getInShopsPrice(),
                                product.getTags(),
                                manufacturerId,
                                priceDtos,
                                product.getAttributes(),
                                productImagesDtos,
                                productInformationResponseDto); // Usa la lista di DTO

        }

        public static ProductEntity fromResponseToEntity(ProductResponseDto dto) {
                ProductEntity product = new ProductEntity();
                product.setId(dto.getId());
                product.setSku(dto.getSku());
                product.setWeight(dto.getWeight());
                product.setCategory(dto.getCategory());
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
                                                priceEntity.getPrice(),
                                                product.getId()))
                                .collect(Collectors.toList());

                return new ProductRequestDto(
                                product.getId(),
                                product.getSku(),
                                product.getRetailPrice(),
                                product.getCategory(),
                                product.getWeight(),
                                product.getActive(),
                                product.getWholesalePrice(),
                                product.getInShopsPrice(),
                                priceLargeQuantities);
        }

}
