package com.ortoroverbasso.ortorovebasso.service.user.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.User.UserRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.User.UserResponseDto;
import com.ortoroverbasso.ortorovebasso.mapper.UserMapper;
import com.ortoroverbasso.ortorovebasso.model.User;
import com.ortoroverbasso.ortorovebasso.repository.UserRepository;
import com.ortoroverbasso.ortorovebasso.service.user.IUserService;

@Service
public class UserServiceImpl extends IUserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public List<UserResponseDto> getAllUsers() {
    return userRepository.findAll()
        .stream()
        .map(UserMapper::toResponseDto)
        .collect(Collectors.toList());
  }

  public UserResponseDto createUser(UserRequestDto dto) {
    User user = UserMapper.toEntity(dto);
    String encrypted = passwordEncoder.encode(user.getPassword());
    user.setPassword(encrypted);
    User saved = userRepository.save(user);
    return UserMapper.toResponseDto(saved);
  }

}
