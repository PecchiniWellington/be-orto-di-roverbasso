package com.ortoroverbasso.ortorovebasso.mapper.product;

import com.ortoroverbasso.ortorovebasso.dto.ProductInformation.ProductInformationRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.ProductInformation.ProductInformationResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductInformationEntity;

public class ProductInformationMapper {
    public static ProductInformationEntity toEntity(ProductInformationRequestDto dto) {
        ProductInformationEntity productInformation = new ProductInformationEntity();

        productInformation.setId(dto.getId());
        productInformation.setSku(dto.getSku());
        productInformation.setName(dto.getName());
        productInformation.setDescription(dto.getDescription());
        productInformation.setUrl(dto.getUrl());
        productInformation.setIsoCode(dto.getIsoCode());
        productInformation.setDateUpdDescription(dto.getDateUpdDescription());

        return productInformation;
    }

    public static ProductInformationResponseDto toResponseDto(ProductInformationEntity productInformation) {
        return new ProductInformationResponseDto(
                productInformation.getId(),
                productInformation.getSku(),
                productInformation.getName(),
                productInformation.getDescription(),
                productInformation.getUrl(),
                productInformation.getIsoCode(),
                productInformation.getDateUpdDescription());
    }

}
