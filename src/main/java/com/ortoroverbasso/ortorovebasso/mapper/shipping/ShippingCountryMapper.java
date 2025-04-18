package com.ortoroverbasso.ortorovebasso.mapper.shipping;

import com.ortoroverbasso.ortorovebasso.dto.shipping.ShippingCountryRequestDto;
import com.ortoroverbasso.ortorovebasso.entity.shipping.ShippingCountryEntity;

public class ShippingCountryMapper {

    public static ShippingCountryEntity toEntity(ShippingCountryRequestDto dto) {
        ShippingCountryEntity entity = new ShippingCountryEntity();
        entity.setIsoCountry(dto.getIsoCountry());
        return entity;
    }

    public static ShippingCountryRequestDto toRequestDto(ShippingCountryEntity entity) {
        ShippingCountryRequestDto dto = new ShippingCountryRequestDto();
        dto.setIsoCountry(entity.getIsoCountry());
        return dto;
    }
}
