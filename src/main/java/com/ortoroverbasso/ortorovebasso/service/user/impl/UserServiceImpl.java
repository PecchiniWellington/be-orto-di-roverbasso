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
import com.ortoroverbasso.ortorovebasso.service.images.IImagesDetailService;
import com.ortoroverbasso.ortorovebasso.service.user.IUserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private IImagesDetailService imagesDetailService;

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
        System.out.print("Attempting to delete user with ID: {}" + id);
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(userEntity);

        UserResponseDto responseDto = UserMapper.toResponseDto(userEntity);
        System.out.print("User with ID {}  has been deleted." + userEntity.getId());
        System.out.print("name {} has been deleted." + userEntity.getName());
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
        System.out.print("Retrieving current authenticated user from service");

        try {
            // Get the current authentication from the security context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null) {
                System.out.print("Authentication is null");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No authentication found");
            }

            System.out.print("Authentication object: {}" + authentication);
            System.out.print("Authentication name: {}" + authentication.getName());
            System.out.print("Authentication principal: {}" + authentication.getPrincipal());
            System.out.print("Authentication authorities: {}" + authentication.getAuthorities());

            // Check for anonymous authentication
            if (authentication.getPrincipal() instanceof String &&
                    "anonymousUser".equals(authentication.getPrincipal())) {
                System.out.print("Received anonymous authentication");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }

            // Check if the user is authenticated
            if (authentication.isAuthenticated()) {
                try {
                    String username = authentication.getName();
                    System.out.print("Looking up user by email: {}" + username);

                    // Verify the username is not null or empty
                    if (username == null || username.isEmpty()) {
                        System.out.print("Username is null or empty");
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body("Invalid username in authentication");
                    }

                    // Retrieve the user based on the authentication
                    UserEntity user = userRepository.findByEmail(username)
                            .orElseThrow(() -> new UserNotFoundException("User not found with email: " + username));

                    System.out.print("Current authenticated user found: id={}, email={}, role={}" + user.getId());
                    System.out.print("Current authenticated user found: id={}, email={}, role={}" +
                            user.getEmail());
                    System.out.print("Current authenticated user found: id={}, email={}, role={}" + user.getRole());

                    // Generate a token
                    String token = tokenProvider.generateToken(user);
                    System.out.print("Generated token for user");

                    Date expiryDate = tokenProvider.getExpirationDateFromToken(token);
                    System.out.print("Token expiry date: {}" + expiryDate);

                    // Create response dto
                    JwtAuthResponseDto responseDto = new JwtAuthResponseDto(
                            token,
                            user.getId(),
                            user.getEmail(),
                            user.getRole().name(),
                            expiryDate);

                    // Return user info
                    return ResponseEntity.ok(responseDto);
                } catch (UserNotFoundException e) {
                    System.out.print("User not found: {}" + e.getMessage());
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("User not found: " + e.getMessage());
                } catch (Exception e) {
                    System.out.print("Error processing authenticated user: {}" + e.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error processing authenticated user: " + e.getMessage());
                }
            }

            System.out.print("User is not authenticated");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        } catch (Exception e) {
            System.out.print("Unexpected error in getCurrentAuthenticatedUser: {}" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
        }
    }
}
