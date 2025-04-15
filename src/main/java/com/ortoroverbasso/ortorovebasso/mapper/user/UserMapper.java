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
        entity.setFullName(dto.getFullName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setAddress(dto.getAddress());
        entity.setLanguage(dto.getLanguage());
        entity.setTimeZone(dto.getTimeZone());
        entity.setThemePreferenceDark(dto.getThemePreferenceDark());
        entity.setAccountStatus(dto.getAccountStatus());
        entity.setAccountType(dto.getAccountType());
        entity.setSubscriptionStatus(dto.getSubscriptionStatus());
        entity.setCountry(dto.getCountry());
        entity.setRegion(dto.getRegion());
        entity.setNotificationPreferences(dto.getNotificationPreferences());
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
        response.setPassword(entity.getPassword());
        response.setRole(entity.getRole());
        response.setFullName(entity.getFullName());
        response.setPhoneNumber(entity.getPhoneNumber());
        response.setAddress(entity.getAddress());
        response.setLanguage(entity.getLanguage());
        response.setTimeZone(entity.getTimeZone());
        response.setThemePreferenceDark(entity.getThemePreferenceDark());
        response.setAccountStatus(entity.getAccountStatus());
        response.setAccountType(entity.getAccountType());
        response.setSubscriptionStatus(entity.getSubscriptionStatus());
        response.setCountry(entity.getCountry());
        response.setRegion(entity.getRegion());
        response.setNotificationPreferences(entity.getNotificationPreferences());
        // Add additional mappings as needed
        return response;
    }

    public static List<UserResponseDto> toResponseDto(List<UserEntity> users) {
        return users.stream()
                .map(UserMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
