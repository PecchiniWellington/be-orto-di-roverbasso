package com.ortoroverbasso.ortorovebasso.controller.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ortoroverbasso.ortorovebasso.dto.user.UserRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserResponseDto;
import com.ortoroverbasso.ortorovebasso.service.user.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "User management API")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequest) {
        return userService.createUser(userRequest);
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable Long id, @RequestBody UserRequestDto entity) {
        return userService.updateUser(id, entity);
    }

    @DeleteMapping("/{id}")
    public UserResponseDto deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @Operation(summary = "Upload user avatar", description = "Upload a new avatar for a user")
    @PostMapping("/{userId}/avatar/upload")
    public ResponseEntity<UserResponseDto> uploadAvatar(
            @PathVariable Long userId,
            @RequestPart("file") MultipartFile file) {
        return userService.uploadAvatar(userId, file);
    }

    @Operation(summary = "Update user avatar", description = "Update a user's avatar with an existing image")
    @PutMapping("/{userId}/avatar/{imageId}")
    public ResponseEntity<UserResponseDto> updateAvatar(
            @PathVariable Long userId,
            @PathVariable Long imageId) {
        return userService.updateAvatar(userId, imageId);
    }

    @Operation(summary = "Delete user avatar", description = "Remove a user's avatar")
    @DeleteMapping("/{userId}/avatar")
    public ResponseEntity<UserResponseDto> deleteAvatar(@PathVariable Long userId) {
        return userService.deleteAvatar(userId);
    }

    @Operation(summary = "Get current authenticated user", description = "Get details of the current authenticated user")
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentAuthenticatedUser() {
        try {
            return userService.getCurrentAuthenticatedUser();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error checking authentication: " + e.getMessage());
        }
    }
}
