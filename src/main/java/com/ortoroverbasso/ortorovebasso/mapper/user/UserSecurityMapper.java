package com.ortoroverbasso.ortorovebasso.mapper.user;

import java.util.List;
import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.user.UserSecurityRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserSecurityResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.user.user_security.UserSecurityEntity;

public class UserSecurityMapper {

    public static UserSecurityEntity toEntity(UserSecurityRequestDto dto) {
        if (dto == null) {
            return null;
        }

        UserSecurityEntity entity = new UserSecurityEntity();
        entity.setOtpSecret(dto.getOtpSecret());
        entity.setMfaEnabled(dto.isMfaEnabled());
        entity.setRecoveryKeys(dto.getRecoveryKeys());
        entity.setLastLoginAt(dto.getLastLoginAt());
        entity.setLastLoginIp(dto.getLastLoginIp());

        return entity;
    }

    public static UserSecurityResponseDto toResponseDto(UserSecurityEntity entity) {
        if (entity == null) {
            return null;
        }

        UserSecurityResponseDto response = new UserSecurityResponseDto();
        response.setId(entity.getId());
        response.setMfaEnabled(entity.isMfaEnabled());
        response.setLastLoginAt(entity.getLastLoginAt());
        response.setLastLoginIp(entity.getLastLoginIp());
        // Note: For security reasons, we typically don't include sensitive data like
        // OTP secrets
        // or recovery keys in response DTOs

        return response;
    }

    public static List<UserSecurityResponseDto> toResponseDto(List<UserSecurityEntity> securityRecords) {
        return securityRecords.stream()
                .map(UserSecurityMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
