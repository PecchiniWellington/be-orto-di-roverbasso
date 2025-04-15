package com.ortoroverbasso.ortorovebasso.service.user.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.user.UserRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.mapper.user.UserMapper;
import com.ortoroverbasso.ortorovebasso.repository.user.UserRepository;
import com.ortoroverbasso.ortorovebasso.service.user.IUserService;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<UserEntity> usersEntity = userRepository.findAll();

        return usersEntity.stream().map(user -> UserMapper.toResponseDto(user)).toList();
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toResponseDto(userEntity);
    }

    @Override
    public UserResponseDto deleteUser(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(userEntity);

        // Crea il DTO e imposta un messaggio
        UserResponseDto responseDto = UserMapper.toResponseDto(userEntity);
        responseDto.setMessage(
                "User with ID " + userEntity.getId() + " and name " + userEntity.getName() + " has been deleted.");
        return responseDto;
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserByEmail'");
    }

    @Override
    public UserResponseDto createUser(UserRequestDto user) {
        UserEntity userEntity = UserMapper.toEntity(user);
        userEntity.setId(null); // Ensure the ID is null for a new entity

        UserEntity savedUser = userRepository.save(userEntity);

        return UserMapper.toResponseDto(savedUser);
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto user) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());

        UserEntity updatedUser = userRepository.save(userEntity);

        return UserMapper.toResponseDto(updatedUser);
    }

}
