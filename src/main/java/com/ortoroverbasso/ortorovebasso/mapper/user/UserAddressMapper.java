package com.ortoroverbasso.ortorovebasso.mapper.user;

import java.util.List;
import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.user.UserAddressRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserAddressResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.user.user_address.UserAddressEntity;

public class UserAddressMapper {

    public static UserAddressEntity toEntity(UserAddressRequestDto dto) {
        if (dto == null) {
            return null;
        }

        UserAddressEntity entity = new UserAddressEntity();
        entity.setStreetAddress(dto.getStreetAddress());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setPostalCode(dto.getPostalCode());
        entity.setCountry(dto.getCountry());
        entity.setPrimary(dto.isPrimary());

        return entity;
    }

    public static UserAddressResponseDto toResponseDto(UserAddressEntity entity) {
        if (entity == null) {
            return null;
        }

        UserAddressResponseDto response = new UserAddressResponseDto();
        response.setId(entity.getId());
        response.setStreetAddress(entity.getStreetAddress());
        response.setCity(entity.getCity());
        response.setState(entity.getState());
        response.setPostalCode(entity.getPostalCode());
        response.setCountry(entity.getCountry());
        response.setPrimary(entity.isPrimary());

        return response;
    }

    public static List<UserAddressResponseDto> toResponseDto(List<UserAddressEntity> addresses) {
        return addresses.stream()
                .map(UserAddressMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
