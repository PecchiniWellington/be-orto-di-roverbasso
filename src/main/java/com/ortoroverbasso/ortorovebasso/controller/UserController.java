package com.ortoroverbasso.ortorovebasso.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.User.UserRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.User.UserResponseDto;
import com.ortoroverbasso.ortorovebasso.service.user.impl.UserServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserServiceImpl userService;

  public UserController(UserServiceImpl userService) {
    this.userService = userService;
  }

  @Operation(summary = "Recupera tutti gli utenti registrati")
  @GetMapping
  public List<UserResponseDto> getAll() {
    return userService.getAllUsers();
  }

  @Operation(summary = "Crea un nuovo utente")
  @ApiResponse(responseCode = "400", description = "Errore di validazione multipla")
  @PostMapping
  public UserResponseDto create(@RequestBody UserRequestDto dto) {
    return userService.createUser(dto);
  }

}
