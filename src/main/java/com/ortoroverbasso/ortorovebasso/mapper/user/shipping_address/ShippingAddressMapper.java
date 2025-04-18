package com.ortoroverbasso.ortorovebasso.mapper.user.shipping_address;

import com.ortoroverbasso.ortorovebasso.dto.user.shipping_address.ShippingAddressRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.shipping_address.ShippingAddressResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.user.shipping_address.ShippingAddressEntity;

public class ShippingAddressMapper {

    public static ShippingAddressEntity toEntity(ShippingAddressRequestDto dto) {
        ShippingAddressEntity entity = new ShippingAddressEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setCountry(dto.getCountry());
        entity.setPostcode(dto.getPostcode());
        entity.setTown(dto.getTown());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setVatNumber(dto.getVatNumber());
        entity.setCompanyName(dto.getCompanyName());
        entity.setComment(dto.getComment());
        return entity;
    }

    public static ShippingAddressResponseDto toResponse(ShippingAddressEntity entity) {
        ShippingAddressResponseDto dto = new ShippingAddressResponseDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setCountry(entity.getCountry());
        dto.setPostcode(entity.getPostcode());
        dto.setTown(entity.getTown());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setVatNumber(entity.getVatNumber());
        dto.setCompanyName(entity.getCompanyName());
        dto.setComment(entity.getComment());
        return dto;
    }
}
