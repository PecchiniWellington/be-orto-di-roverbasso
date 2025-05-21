package com.ortoroverbasso.ortorovebasso.service.user;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ortoroverbasso.ortorovebasso.dto.user.UserRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;

public interface IUserService {
    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long id);

    ResponseEntity<UserResponseDto> createUser(UserRequestDto userRequest);

    UserResponseDto updateUser(Long id, UserRequestDto user);

    UserResponseDto deleteUser(Long id);

    UserResponseDto getUserByEmail(String email);

    // Add this new method for current authenticated user
    ResponseEntity<?> getCurrentAuthenticatedUser();

    Long getUserIdFromEmail(String email);

    public UserEntity findOrCreateFromGoogle(String email, String name, String pictureUrl);

    ResponseEntity<?> getCurrentAuthenticatedUserByEmail(String email);

}
