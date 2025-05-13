package com.ortoroverbasso.ortorovebasso.mapper.user;

import java.util.List;
import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.user.UserRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(UserRequestDto dto) {
        if (dto == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());
        entity.setAccountStatus(dto.getAccountStatus());

        // Handle profile separately with ProfileMapper if needed
        // Handle other relationships as needed

        return entity;
    }

    public static UserResponseDto toResponseDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        UserResponseDto response = new UserResponseDto();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setEmail(entity.getEmail());
        // Don't include password in response
        response.setRole(entity.getRole());
        response.setAccountStatus(entity.getAccountStatus());

        // Map related entities if needed
        if (entity.getProfile() != null) {
            response.setProfile(UserProfileMapper.toResponseDto(entity.getProfile()));
        }

        if (entity.getAddresses() != null && !entity.getAddresses().isEmpty()) {
            response.setAddresses(UserAddressMapper.toResponseDto(entity.getAddresses()));
        }

        if (entity.getPreferences() != null) {
            response.setPreferences(UserPreferencesMapper.toResponseDto(entity.getPreferences()));
        }

        // Add additional mappings as needed
        return response;
    }

    public static List<UserResponseDto> toResponseDto(List<UserEntity> users) {
        return users.stream()
                .map(UserMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
