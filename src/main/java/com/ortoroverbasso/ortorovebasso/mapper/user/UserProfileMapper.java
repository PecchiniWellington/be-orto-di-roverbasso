package com.ortoroverbasso.ortorovebasso.mapper.user;

import java.util.List;
import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.user.UserProfileRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserProfileResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.user.user_profile.UserProfileEntity;

public class UserProfileMapper {

    public static UserProfileEntity toEntity(UserProfileRequestDto dto) {
        if (dto == null) {
            return null;
        }

        UserProfileEntity entity = new UserProfileEntity();
        entity.setBio(dto.getBio());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setBirthDate(dto.getBirthDate());
        entity.setGender(dto.getGender());
        entity.setAvatarId(dto.getAvatarId());

        return entity;
    }

    public static UserProfileResponseDto toResponseDto(UserProfileEntity entity) {
        if (entity == null) {
            return null;
        }

        UserProfileResponseDto response = new UserProfileResponseDto();
        response.setId(entity.getId());
        response.setBio(entity.getBio());
        response.setPhoneNumber(entity.getPhoneNumber());
        response.setBirthDate(entity.getBirthDate());
        response.setGender(entity.getGender());
        response.setAvatarId(entity.getAvatarId());

        return response;
    }

    public static List<UserProfileResponseDto> toResponseDto(List<UserProfileEntity> profiles) {
        return profiles.stream()
                .map(UserProfileMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
