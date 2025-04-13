package com.ortoroverbasso.ortorovebasso.service.user.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.User.UserRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.User.UserResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.mapper.UserMapper;
import com.ortoroverbasso.ortorovebasso.repository.UserRepository;
import com.ortoroverbasso.ortorovebasso.service.user.IUserService;
import com.ortoroverbasso.ortorovebasso.service.validation.UserRequestValidator;

@Service
public class UserServiceImpl implements IUserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserResponseDto createUser(UserRequestDto dto) {
    // Validazione
    UserRequestValidator.validate(dto);

    // Conversione da DTO a entità
    UserEntity user = UserMapper.toEntity(dto);

    // Salvataggio nel database
    UserEntity savedUser = userRepository.save(user);

    // Conversione da entità a DTO per la risposta
    return UserMapper.toResponseDto(savedUser);
  }

  @Override
  public List<UserResponseDto> getAllUsers() {
    // Recupero tutti gli utenti dal database
    return userRepository.findAll()
        .stream()
        .map(UserMapper::toResponseDto) // Conversione da entità a DTO
        .collect(Collectors.toList());
  }

  @Override
  public UserResponseDto getUserById(Long id) {
    // Recupero un utente per ID, se non trovato lancia un'eccezione
    return userRepository.findById(id)
        .map(UserMapper::toResponseDto) // Conversione da entità a DTO
        .orElseThrow(() -> new RuntimeException("User not found"));
  }

}
