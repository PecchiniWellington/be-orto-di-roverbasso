package com.ortoroverbasso.ortorovebasso.mapper.user;

import java.util.List;
import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.user.UserPreferencesRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserPreferencesResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.user.user_preferences.UserPreferencesEntity;

public class UserPreferencesMapper {

    public static UserPreferencesEntity toEntity(UserPreferencesRequestDto dto) {

        if (dto == null) {
            return null;
        }
        System.out.println("[DEBUG MAPPER] Mapping privacySettings: " + dto.getPrivacySettings());

        UserPreferencesEntity entity = new UserPreferencesEntity();
        entity.setEmailNotificationsEnabled(dto.isEmailNotificationsEnabled());
        entity.setPushNotificationsEnabled(dto.isPushNotificationsEnabled());
        entity.setPreferredLanguage(dto.getPreferredLanguage());
        entity.setPrivacySettings(dto.getPrivacySettings());

        return entity;
    }

    public static UserPreferencesResponseDto toResponseDto(UserPreferencesEntity entity) {
        if (entity == null) {
            return null;
        }

        UserPreferencesResponseDto response = new UserPreferencesResponseDto();
        response.setId(entity.getId());
        response.setEmailNotificationsEnabled(entity.isEmailNotificationsEnabled());
        response.setPushNotificationsEnabled(entity.isPushNotificationsEnabled());
        response.setPreferredLanguage(entity.getPreferredLanguage());
        response.setPrivacySettings(entity.getPrivacySettings());

        return response;
    }

    public static List<UserPreferencesResponseDto> toResponseDto(List<UserPreferencesEntity> preferences) {
        return preferences.stream()
                .map(UserPreferencesMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
