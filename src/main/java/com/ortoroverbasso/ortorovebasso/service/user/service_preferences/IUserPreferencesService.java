package com.ortoroverbasso.ortorovebasso.service.user.service_preferences;

import com.ortoroverbasso.ortorovebasso.dto.user.UserPreferencesRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserPreferencesResponseDto;

public interface IUserPreferencesService {

    UserPreferencesResponseDto create(Long userId, UserPreferencesRequestDto dto);

    UserPreferencesResponseDto update(Long userId, UserPreferencesRequestDto dto);

    UserPreferencesResponseDto get(Long userId);

    void delete(Long userId);
}
