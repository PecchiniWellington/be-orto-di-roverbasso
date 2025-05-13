package com.ortoroverbasso.ortorovebasso.service.user.service_preferences;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ortoroverbasso.ortorovebasso.dto.user.UserPreferencesRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserPreferencesResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.user_preferences.UserPreferencesEntity;
import com.ortoroverbasso.ortorovebasso.exception.UserNotFoundException;
import com.ortoroverbasso.ortorovebasso.mapper.user.UserPreferencesMapper;
import com.ortoroverbasso.ortorovebasso.repository.user.UserRepository;
import com.ortoroverbasso.ortorovebasso.repository.user.user_preferences.UserPreferencesRepository;

@Service
public class UserPreferencesServiceImpl implements IUserPreferencesService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPreferencesRepository userPreferencesRepository;

    @Override
    @Transactional
    public UserPreferencesResponseDto create(Long userId, UserPreferencesRequestDto dto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        UserPreferencesEntity preferences = UserPreferencesMapper.toEntity(dto);
        preferences.setUser(user);
        user.setPreferences(preferences);

        userPreferencesRepository.save(preferences);
        return UserPreferencesMapper.toResponseDto(preferences);
    }

    @Override
    @Transactional
    public UserPreferencesResponseDto update(Long userId, UserPreferencesRequestDto dto) {
        UserPreferencesEntity preferences = userPreferencesRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Preferences not found"));

        preferences.setEmailNotificationsEnabled(dto.isEmailNotificationsEnabled());
        preferences.setPushNotificationsEnabled(dto.isPushNotificationsEnabled());
        preferences.setPreferredLanguage(dto.getPreferredLanguage());
        preferences.setPrivacySettings(dto.getPrivacySettings());

        userPreferencesRepository.save(preferences);
        return UserPreferencesMapper.toResponseDto(preferences);
    }

    @Override
    public UserPreferencesResponseDto get(Long userId) {
        UserPreferencesEntity preferences = userPreferencesRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Preferences not found"));
        return UserPreferencesMapper.toResponseDto(preferences);
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        userPreferencesRepository.findByUserId(userId).ifPresent(userPreferencesRepository::delete);
    }
}
