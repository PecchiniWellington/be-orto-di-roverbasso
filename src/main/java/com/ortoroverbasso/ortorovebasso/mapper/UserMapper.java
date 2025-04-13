package com.ortoroverbasso.ortorovebasso.mapper;

import com.ortoroverbasso.ortorovebasso.dto.User.UserRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.User.UserResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;

public class UserMapper {
  public static UserResponseDto toResponseDto(UserEntity user) {
    return new UserResponseDto(user.getId(), user.getName(), user.getEmail());
  }

  public static UserEntity toEntity(UserRequestDto dto) {
    UserEntity user = new UserEntity();
    user.setName(dto.getName());
    user.setEmail(dto.getEmail());
    user.setPassword(dto.getPassword());
    return user;
  }
}
