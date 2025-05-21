package com.ortoroverbasso.ortorovebasso.mapper.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.ortoroverbasso.ortorovebasso.dto.user.UserProfileResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(UserRequestDto dto) {
        if (dto == null)
            return null;

        UserEntity entity = new UserEntity();

        if (StringUtils.hasText(dto.getName())) {
            entity.setName(dto.getName());
        }

        if (StringUtils.hasText(dto.getEmail())) {
            entity.setEmail(dto.getEmail());
        }

        if (StringUtils.hasText(dto.getPassword())) {
            entity.setPassword(dto.getPassword());
        }

        if (dto.getRole() != null) {
            entity.setRole(dto.getRole());
        }

        if (dto.getAccountStatus() != null) {
            entity.setAccountStatus(dto.getAccountStatus());
        }

        if (StringUtils.hasText(dto.getProvider())) {
            entity.setProvider(dto.getProvider());
        }

        return entity;
    }

    public static UserResponseDto toResponseDto(UserEntity entity) {
        if (entity == null)
            return null;

        UserResponseDto response = new UserResponseDto();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setEmail(entity.getEmail());
        response.setRole(entity.getRole());
        response.setAccountStatus(entity.getAccountStatus());
        response.setProvider(entity.getProvider());

        if (entity.getProfile() != null) {
            response.setProfile(UserProfileMapper.toResponseDto(entity.getProfile()));

            if (entity.getProfile().getAvatar() != null) {
                UserProfileResponseDto profileDto = UserProfileMapper.toResponseDto(entity.getProfile());

                if (entity.getProfile().getAvatar() != null && entity.getProfile().getAvatar().getUrl() != null) {
                    profileDto.setAvatarUrl(entity.getProfile().getAvatar().getUrl());
                } else if (entity.getSecurity() != null
                        && entity.getSecurity().getUser().getProfile().getAvatar() != null) {
                    profileDto.setAvatarUrl(entity.getSecurity().getUser().getProfile().getAvatar().getUrl());
                }

                response.setProfile(profileDto);
            }
        }

        if (entity.getAddresses() != null && !entity.getAddresses().isEmpty()) {
            response.setAddresses(UserAddressMapper.toResponseDto(entity.getAddresses()));
        }

        if (entity.getPreferences() != null) {
            response.setPreferences(UserPreferencesMapper.toResponseDto(entity.getPreferences()));
        }

        return response;
    }

    public static List<UserResponseDto> toResponseDto(List<UserEntity> users) {
        return users.stream()
                .map(UserMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
