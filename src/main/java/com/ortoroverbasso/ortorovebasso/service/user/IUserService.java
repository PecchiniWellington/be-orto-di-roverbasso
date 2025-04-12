package com.ortoroverbasso.ortorovebasso.service.user;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.User.UserRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.User.UserResponseDto;

public interface IUserService {
    List<UserResponseDto> getAllUsers();

    UserResponseDto createUser(UserRequestDto dto);

    UserResponseDto getUserById(Long id);
}
