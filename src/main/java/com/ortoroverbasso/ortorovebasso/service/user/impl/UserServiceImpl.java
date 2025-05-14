package com.ortoroverbasso.ortorovebasso.service.user.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.auth.JwtAuthResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.exception.UserNotFoundException;
import com.ortoroverbasso.ortorovebasso.mapper.user.UserMapper;
import com.ortoroverbasso.ortorovebasso.repository.user.UserRepository;
import com.ortoroverbasso.ortorovebasso.security.JwtTokenProvider;
import com.ortoroverbasso.ortorovebasso.service.user.IUserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<UserEntity> usersEntity = userRepository.findAll();
        return usersEntity.stream().map(UserMapper::toResponseDto).toList();
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserMapper.toResponseDto(userEntity);
    }

    @Override
    @Transactional
    public ResponseEntity<UserResponseDto> createUser(UserRequestDto userRequest) {
        UserEntity userEntity = UserMapper.toEntity(userRequest);
        userEntity = userRepository.save(userEntity);
        UserResponseDto responseDto = UserMapper.toResponseDto(userEntity);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto user) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());

        UserEntity updatedUser = userRepository.save(userEntity);
        return UserMapper.toResponseDto(updatedUser);
    }

    @Override
    @Transactional
    public UserResponseDto deleteUser(Long id) {
        logger.info("Attempting to delete user with ID: {}", id);
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(userEntity);

        UserResponseDto responseDto = UserMapper.toResponseDto(userEntity);
        logger.info("User deleted: id={}, name={}", userEntity.getId(), userEntity.getName());
        responseDto.setMessage(
                "User with ID " + userEntity.getId() + " and name " + userEntity.getName() + " has been deleted.");
        return responseDto;
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return UserMapper.toResponseDto(userEntity);
    }

    @Override
    public ResponseEntity<?> getCurrentAuthenticatedUser() {
        logger.info("Retrieving current authenticated user from service");

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()
                    || "anonymousUser".equals(authentication.getPrincipal())) {
                logger.warn("User not authenticated");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }

            String username = authentication.getName();
            if (username == null || username.isEmpty()) {
                logger.warn("Username is null or empty");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username in authentication");
            }

            UserEntity user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found with email: " + username));

            String token = tokenProvider.generateToken(user);
            Date expiryDate = tokenProvider.getExpirationDateFromToken(token);

            JwtAuthResponseDto responseDto = new JwtAuthResponseDto(
                    token,
                    user.getId(),
                    user.getEmail(),
                    user.getRole().name(),
                    expiryDate);

            return ResponseEntity.ok(responseDto);
        } catch (UserNotFoundException e) {
            logger.error("User not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error processing authenticated user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    // ✅ Metodo spostato da JwtUtil
    @Override
    public Long getUserIdFromEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return user.getId();
    }
}
