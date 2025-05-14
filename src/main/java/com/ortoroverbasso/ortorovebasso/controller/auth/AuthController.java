package com.ortoroverbasso.ortorovebasso.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.auth.JwtAuthResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.auth.LoginRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserRequestDto;
import com.ortoroverbasso.ortorovebasso.service.auth.IAuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> login(
            @RequestBody LoginRequestDto loginDto,
            HttpServletRequest request,
            HttpServletResponse response) {
        return authService.login(loginDto, request, response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDto dto) {
        return authService.register(dto);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        return authService.logout(response);
    }
}
