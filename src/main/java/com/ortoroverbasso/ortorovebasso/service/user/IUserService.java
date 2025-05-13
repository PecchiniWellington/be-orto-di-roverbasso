package com.ortoroverbasso.ortorovebasso.service.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.ortoroverbasso.ortorovebasso.dto.user.UserRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserResponseDto;

public interface IUserService {
    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long id);

    ResponseEntity<UserResponseDto> createUser(UserRequestDto userRequest);

    UserResponseDto updateUser(Long id, UserRequestDto user);

    UserResponseDto deleteUser(Long id);

    UserResponseDto getUserByEmail(String email);

    // Add this new method for current authenticated user
    ResponseEntity<?> getCurrentAuthenticatedUser();

    // Add methods for user avatar management
    ResponseEntity<UserResponseDto> uploadAvatar(Long userId, MultipartFile file);

    ResponseEntity<UserResponseDto> updateAvatar(Long userId, Long imageId);

    ResponseEntity<UserResponseDto> deleteAvatar(Long userId);
}
