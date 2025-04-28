package com.ortoroverbasso.ortorovebasso.mapper.product.product_images;

import com.ortoroverbasso.ortorovebasso.dto.images.ImagesDetailDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_images.ProductImagesResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.product_images.ProductImageEntity;

public class ProductImagesMapper {

    public static ProductImageEntity toEntity(
            ImagesDetailDto dto,
            String name,
            String url,
            Long productId) {

        ProductImageEntity image = new ProductImageEntity();
        image.setName(name);
        image.setUrl(url);
        image.setProductId(productId);
        image.setIsCover(dto.isCover());
        image.setLogo(dto.isLogo());
        image.setWhiteBackground(dto.isWhiteBackground());
        image.setPosition(dto.getPosition());
        image.setEnergyEfficiency(dto.getEnergyEfficiency());
        image.setIcon(dto.getIcon());
        image.setMarketingPhoto(dto.getMarketingPhoto());
        image.setPackagingPhoto(dto.getPackagingPhoto());
        image.setBrand(dto.getBrand());
        image.setGpsrLabel(dto.isGpsrLabel());
        image.setGpsrWarning(dto.isGpsrWarning());

        return image;
    }

    public static ProductImagesResponseDto toResponse(ProductImageEntity entity) {
        /*
         * return new ProductImagesResponseDto(
         * entity.getId(),
         * entity.getName(),
         * entity.getUrl(),
         * entity.isCover(),
         * entity.isLogo(),
         * entity.isWhiteBackground(),
         * entity.getPosition(),
         * entity.getEnergyEfficiency(),
         * entity.getIcon(),
         * entity.getMarketingPhoto(),
         * entity.getPackagingPhoto(),
         * entity.getBrand(),
         * entity.isGpsrLabel(),
         * entity.isGpsrWarning(),
         * entity.getProductId());
         * }
         */

        if (entity == null) {

            return null;

        }

        ProductImagesResponseDto response = new ProductImagesResponseDto();

        // Map fields from ProductImageEntity to ProductImagesResponseDto

        if (entity == null) {

            return null;

        }

        response.setId(entity.getId());

        response.setProductId(entity.getProductId());

        response.setUrl(entity.getUrl());
        response.setName(entity.getName());
        response.setCover(entity.isCover());
        response.setLogo(entity.isLogo());
        response.setWhiteBackground(entity.isWhiteBackground());
        response.setPosition(entity.getPosition());
        response.setEnergyEfficiency(entity.getEnergyEfficiency());
        response.setIcon(entity.getIcon());
        response.setMarketingPhoto(entity.getMarketingPhoto());
        response.setBrand(entity.getBrand());
        response.setGpsrLabel(entity.isGpsrLabel());
        response.setGpsrWarning(entity.isGpsrWarning());
        response.setProductId(entity.getProductId());

        return response;
    }

}
