package com.ortoroverbasso.ortorovebasso.service.user.user_profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ortoroverbasso.ortorovebasso.dto.user.UserProfileRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserProfileResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.user_profile.UserProfileEntity;
import com.ortoroverbasso.ortorovebasso.mapper.user.UserProfileMapper;
import com.ortoroverbasso.ortorovebasso.repository.user.UserRepository;
import com.ortoroverbasso.ortorovebasso.repository.user.user_profile.UserProfileRepository;

@Service
public class UserProfileServiceImpl implements IUserProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    @Transactional
    public UserProfileResponseDto create(Long userId, UserProfileRequestDto dto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfileEntity profile = UserProfileMapper.toEntity(dto);
        profile.setUser(user);
        user.setProfile(profile);

        userProfileRepository.save(profile);
        return UserProfileMapper.toResponseDto(profile);
    }

    @Override
    @Transactional
    public UserProfileResponseDto update(Long userId, UserProfileRequestDto dto) {
        UserProfileEntity profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        if (dto.getBio() != null && !dto.getBio().isBlank()) {
            profile.setBio(dto.getBio());
        }
        if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().isBlank()) {
            profile.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getBirthDate() != null) {
            profile.setBirthDate(dto.getBirthDate());
        }
        if (dto.getGender() != null) {
            profile.setGender(dto.getGender());
        }
        if (dto.getAvatarId() != null) {
            profile.setAvatarId(dto.getAvatarId());
        }

        userProfileRepository.save(profile);
        return UserProfileMapper.toResponseDto(profile);
    }

    @Override
    public UserProfileResponseDto get(Long userId) {
        UserProfileEntity profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        return UserProfileMapper.toResponseDto(profile);
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        UserProfileEntity profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        userProfileRepository.delete(profile);
    }
}
