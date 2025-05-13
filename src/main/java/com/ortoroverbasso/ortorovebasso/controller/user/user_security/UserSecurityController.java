package com.ortoroverbasso.ortorovebasso.controller.user.user_security;

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

import com.ortoroverbasso.ortorovebasso.dto.user.UserSecurityRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserSecurityResponseDto;
import com.ortoroverbasso.ortorovebasso.service.user.user_security.IUserSecurityService;

@RestController
@RequestMapping("/api/users/{userId}/security")
public class UserSecurityController {

    @Autowired
    private IUserSecurityService securityService;

    @PostMapping
    public ResponseEntity<UserSecurityResponseDto> create(@PathVariable Long userId,
            @RequestBody UserSecurityRequestDto dto) {
        return ResponseEntity.ok(securityService.create(userId, dto));
    }

    @PutMapping
    public ResponseEntity<UserSecurityResponseDto> update(@PathVariable Long userId,
            @RequestBody UserSecurityRequestDto dto) {
        return ResponseEntity.ok(securityService.update(userId, dto));
    }

    @GetMapping
    public ResponseEntity<UserSecurityResponseDto> get(@PathVariable Long userId) {
        return ResponseEntity.ok(securityService.getByUserId(userId));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Long userId) {
        securityService.deleteByUserId(userId);
        return ResponseEntity.noContent().build();
    }
}
