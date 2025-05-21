// UserController.java
package com.ortoroverbasso.ortorovebasso.controller.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.user.UserRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserResponseDto;
import com.ortoroverbasso.ortorovebasso.service.user.IUserService;

@RestController
@RequestMapping("/api/users")
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

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentAuthenticatedUser(@AuthenticationPrincipal OAuth2User principal) {
        try {
            if (principal != null && principal.getAttributes() != null) {
                String email = (String) principal.getAttributes().get("email");
                return userService.getCurrentAuthenticatedUserByEmail(email);
            }
            return userService.getCurrentAuthenticatedUser();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error checking authentication: " + e.getMessage());
        }
    }
}
