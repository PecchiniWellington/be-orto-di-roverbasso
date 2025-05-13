package com.ortoroverbasso.ortorovebasso.controller.user.user_preferences;

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

import com.ortoroverbasso.ortorovebasso.dto.user.UserPreferencesRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserPreferencesResponseDto;
import com.ortoroverbasso.ortorovebasso.service.user.service_preferences.IUserPreferencesService;

@RestController
@RequestMapping("/api/users/{userId}/preferences")
public class UserPreferencesController {

    @Autowired
    private IUserPreferencesService preferencesService;

    @PostMapping
    public ResponseEntity<UserPreferencesResponseDto> create(
            @PathVariable Long userId,
            @RequestBody UserPreferencesRequestDto dto) {
        System.out.println("[DEBUG CONTROLLER] Received DTO: " + dto);
        System.out.println("[DEBUG CONTROLLER] privacySettings: " + dto.getPrivacySettings());
        return ResponseEntity.ok(preferencesService.create(userId, dto));
    }

    @PutMapping
    public ResponseEntity<UserPreferencesResponseDto> update(
            @PathVariable Long userId,
            @RequestBody UserPreferencesRequestDto dto) {
        return ResponseEntity.ok(preferencesService.update(userId, dto));
    }

    @GetMapping
    public ResponseEntity<UserPreferencesResponseDto> get(@PathVariable Long userId) {
        return ResponseEntity.ok(preferencesService.get(userId));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Long userId) {
        preferencesService.delete(userId);
        return ResponseEntity.noContent().build();
    }
}
