package com.ortoroverbasso.ortorovebasso.mapper;

import com.ortoroverbasso.ortorovebasso.dto.User.UserRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.User.UserResponseDto;
import com.ortoroverbasso.ortorovebasso.model.User;

public class UserMapper {
    public static UserResponseDto toResponseDto(User user) {
    return new UserResponseDto(user.getId(), user.getName(), user.getEmail());
  }

    public static User toEntity(UserRequestDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
      }
  }
