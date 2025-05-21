package com.ortoroverbasso.ortorovebasso.service.user.user_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.user.UserSecurityRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserSecurityResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.images.ImagesDetailEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.user_profile.UserProfileEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.user_security.UserSecurityEntity;
import com.ortoroverbasso.ortorovebasso.exception.UserNotFoundException;
import com.ortoroverbasso.ortorovebasso.mapper.user.UserSecurityMapper;
import com.ortoroverbasso.ortorovebasso.repository.user.UserRepository;
import com.ortoroverbasso.ortorovebasso.repository.user.user_security.UserSecurityRepository;
import com.ortoroverbasso.ortorovebasso.service.images.IImagesDetailService;

import jakarta.transaction.Transactional;

@Service
public class UserSecurityServiceImpl implements IUserSecurityService {

    @Autowired
    private UserSecurityRepository userSecurityRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IImagesDetailService imageDetailService;

    @Override
    @Transactional
    public UserSecurityResponseDto create(Long userId, UserSecurityRequestDto dto) {
        if (userSecurityRepository.findByUserId(userId).isPresent()) {
            throw new IllegalStateException("Security settings already exist for this user");
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        UserSecurityEntity entity = new UserSecurityEntity();
        entity.setUser(user);
        entity.setOtpSecret(dto.getOtpSecret());
        entity.setMfaEnabled(dto.isMfaEnabled());
        entity.setRecoveryKeys(dto.getRecoveryKeys());
        entity.setLastLoginAt(dto.getLastLoginAt());
        entity.setLastLoginIp(dto.getLastLoginIp());

        return UserSecurityMapper.toResponseDto(userSecurityRepository.save(entity));
    }

    @Override
    @Transactional
    public UserSecurityResponseDto update(Long userId, UserSecurityRequestDto dto) {
        UserSecurityEntity entity = userSecurityRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Security settings not found for update"));

        entity.setOtpSecret(dto.getOtpSecret());
        entity.setMfaEnabled(dto.isMfaEnabled());
        entity.setRecoveryKeys(dto.getRecoveryKeys());
        entity.setLastLoginAt(dto.getLastLoginAt());
        entity.setLastLoginIp(dto.getLastLoginIp());

        return UserSecurityMapper.toResponseDto(userSecurityRepository.save(entity));
    }

    @Override
    public UserSecurityResponseDto getByUserId(Long userId) {
        UserSecurityEntity entity = userSecurityRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User security settings not found"));
        return UserSecurityMapper.toResponseDto(entity);
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        // Elimina avatar da B2 se esiste
        UserProfileEntity profile = user.getProfile();
        if (profile != null && profile.getAvatar() != null) {
            ImagesDetailEntity avatar = profile.getAvatar();
            if (avatar.getFileId() != null) {
                imageDetailService.deleteImage(avatar.getId());
            }
        }

        userRepository.delete(user);
    }
}
