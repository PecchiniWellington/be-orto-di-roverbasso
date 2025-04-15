package com.ortoroverbasso.ortorovebasso.service.user;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.user.UserRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserResponseDto;

public interface IUserService {
    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long id);

    UserResponseDto createUser(UserRequestDto user);

    UserResponseDto updateUser(Long id, UserRequestDto user);

    UserResponseDto deleteUser(Long id);

    UserResponseDto getUserByEmail(String email);

}
