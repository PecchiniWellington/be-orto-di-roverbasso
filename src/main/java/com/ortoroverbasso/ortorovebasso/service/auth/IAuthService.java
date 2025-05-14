package com.ortoroverbasso.ortorovebasso.service.auth;

import org.springframework.http.ResponseEntity;

import com.ortoroverbasso.ortorovebasso.dto.auth.JwtAuthResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.auth.LoginRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserRequestDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthService {

    ResponseEntity<JwtAuthResponseDto> login(LoginRequestDto loginDto, HttpServletRequest request,
            HttpServletResponse response);

    ResponseEntity<?> register(UserRequestDto dto);

    ResponseEntity<?> logout(HttpServletResponse response);
}
