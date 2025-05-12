package com.ortoroverbasso.ortorovebasso.service.user.impl;

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

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private JwtTokenProvider tokenProvider;

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
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserMapper.toResponseDto(userEntity);
    }

    @Override
    @Transactional
    public UserResponseDto deleteUser(Long id) {
        logger.info("Attempting to delete user with ID: {}", id);
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(userEntity);

        // Crea il DTO e imposta un messaggio
        UserResponseDto responseDto = UserMapper.toResponseDto(userEntity);

        logger.info("User with ID {} and name {} has been deleted.", userEntity.getId(), userEntity.getName());
        responseDto.setMessage(
                "User with ID " + userEntity.getId() + " and name " + userEntity.getName() + " has been deleted.");
        return responseDto;
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserByEmail'");
    }

    @Transactional
    @Override
    public ResponseEntity<UserResponseDto> createUser(UserRequestDto userRequest) {
        UserEntity userEntity = UserMapper.toEntity(userRequest);
        userEntity = userRepository.save(userEntity);

        // Crea il DTO di risposta
        UserResponseDto responseDto = UserMapper.toResponseDto(userEntity);

        // Restituisci ResponseEntity con il DTO e status code 201 (CREATED)
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
    public ResponseEntity<?> getCurrentAuthenticatedUser() {
        logger.info("Retrieving current authenticated user");

        // Get the current authentication from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is authenticated (not anonymous)
        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String)) {

            try {
                // Retrieve the user based on the authentication
                UserEntity user = userRepository.findByEmail(authentication.getName())
                        .orElseThrow(() -> new UserNotFoundException("User not found"));

                logger.info("Current authenticated user found: id={}, email={}", user.getId(), user.getEmail());

                // Generate a token (or use the existing one)
                String token = tokenProvider.generateToken(user);

                // Return user info
                return ResponseEntity.ok(new JwtAuthResponseDto(
                        token,
                        user.getId(),
                        user.getEmail(),
                        user.getRole().name(),
                        tokenProvider.getExpirationDateFromToken(token)));
            } catch (Exception e) {
                logger.error("Error retrieving authenticated user: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error retrieving authenticated user: " + e.getMessage());
            }
        }

        logger.warn("No authenticated user found");
        // Return unauthorized if not authenticated
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
    }
}
