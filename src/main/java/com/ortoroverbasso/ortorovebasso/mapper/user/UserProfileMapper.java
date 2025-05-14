package com.ortoroverbasso.ortorovebasso.mapper.user;

import com.ortoroverbasso.ortorovebasso.dto.user.UserProfileRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserProfileResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.images.ImagesDetailEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.user_profile.UserProfileEntity;
import com.ortoroverbasso.ortorovebasso.repository.images.ImagesDetailRepository;

public class UserProfileMapper {

    public static UserProfileResponseDto toResponseDto(UserProfileEntity entity) {
        Long avatarId = entity.getAvatar() != null ? entity.getAvatar().getId() : null;
        String avatarUrl = entity.getAvatar() != null ? entity.getAvatar().getUrl() : null;

        return new UserProfileResponseDto(
                entity.getId(),
                entity.getBio(),
                entity.getPhoneNumber(),
                entity.getBirthDate(),
                entity.getGender(),
                avatarId,
                avatarUrl);
    }

    public static void updateEntity(UserProfileEntity entity, UserProfileRequestDto dto,
            ImagesDetailRepository imagesDetailRepository) {
        entity.setBio(dto.getBio());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setBirthDate(dto.getBirthDate());
        entity.setGender(dto.getGender());

        if (dto.getAvatarId() != null) {
            ImagesDetailEntity avatar = imagesDetailRepository.findById(dto.getAvatarId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Immagine avatar non trovata con ID: " + dto.getAvatarId()));
            entity.setAvatar(avatar);
        }
    }
}
