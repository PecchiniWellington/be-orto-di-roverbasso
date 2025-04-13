package com.ortoroverbasso.ortorovebasso.service.user.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.User.UserRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.User.UserResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.User;
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
    UserRequestValidator.validate(dto);

    User user = UserMapper.toEntity(dto);
    User savedUser = userRepository.save(user);
    return UserMapper.toResponseDto(savedUser);
  }

  @Override
  public List<UserResponseDto> getAllUsers() {
    return userRepository.findAll()
        .stream()
        .map(UserMapper::toResponseDto)
        .collect(Collectors.toList());
  }

  @Override
  public UserResponseDto getUserById(Long id) {
    return userRepository.findById(id)
        .map(UserMapper::toResponseDto)
        .orElseThrow(() -> new RuntimeException("User not found"));
  }

}
