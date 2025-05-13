package com.ortoroverbasso.ortorovebasso.controller.user.user_profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.user.UserProfileRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserProfileResponseDto;
import com.ortoroverbasso.ortorovebasso.service.user.user_profile.IUserProfileService;

@RestController
@RequestMapping("/api/users/{userId}/profile")
public class UserProfileController {

    @Autowired
    private IUserProfileService profileService;

    @PostMapping
    public ResponseEntity<UserProfileResponseDto> create(@PathVariable Long userId,
            @RequestBody UserProfileRequestDto dto) {
        return ResponseEntity.ok(profileService.create(userId, dto));
    }

    @PutMapping
    public ResponseEntity<UserProfileResponseDto> update(@PathVariable Long userId,
            @RequestBody UserProfileRequestDto dto) {
        return ResponseEntity.ok(profileService.update(userId, dto));
    }

    @GetMapping
    public ResponseEntity<UserProfileResponseDto> get(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.get(userId));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Long userId) {
        profileService.delete(userId);
        return ResponseEntity.noContent().build();
    }
}
