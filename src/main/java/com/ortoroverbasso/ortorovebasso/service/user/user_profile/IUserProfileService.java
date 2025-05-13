package com.ortoroverbasso.ortorovebasso.service.user.user_profile;

import com.ortoroverbasso.ortorovebasso.dto.user.UserProfileRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserProfileResponseDto;

public interface IUserProfileService {
    UserProfileResponseDto create(Long userId, UserProfileRequestDto dto);

    UserProfileResponseDto update(Long userId, UserProfileRequestDto dto);

    UserProfileResponseDto get(Long userId);

    void delete(Long userId);
}
