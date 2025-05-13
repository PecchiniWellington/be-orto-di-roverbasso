package com.ortoroverbasso.ortorovebasso.service.user.user_security;

import com.ortoroverbasso.ortorovebasso.dto.user.UserSecurityRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserSecurityResponseDto;

public interface IUserSecurityService {
    UserSecurityResponseDto create(Long userId, UserSecurityRequestDto dto);

    UserSecurityResponseDto update(Long userId, UserSecurityRequestDto dto);

    UserSecurityResponseDto getByUserId(Long userId);

    void deleteByUserId(Long userId);
}
