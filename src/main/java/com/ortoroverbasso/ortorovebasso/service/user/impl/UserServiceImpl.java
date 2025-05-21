package com.ortoroverbasso.ortorovebasso.service.user.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.user.UserRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserSessionDto;
import com.ortoroverbasso.ortorovebasso.entity.user.AccountStatus;
import com.ortoroverbasso.ortorovebasso.entity.user.Role;
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

        if (user.getName() != null && !user.getName().isBlank()) {
            userEntity.setName(user.getName());
        }

        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            userEntity.setEmail(user.getEmail());
        }

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            userEntity.setPassword(user.getPassword());
        }

        UserEntity updatedUser = userRepository.save(userEntity);
        return UserMapper.toResponseDto(updatedUser);
    }

    @Override
    @Transactional
    public UserResponseDto deleteUser(Long id) {

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(userEntity);

        UserResponseDto responseDto = UserMapper.toResponseDto(userEntity);

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
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()
                    || "anonymousUser".equals(authentication.getPrincipal())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }

            String username = authentication.getName();
            if (username == null || username.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username in authentication");
            }

            UserEntity user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found with email: " + username));

            // ✅ Qui usi il tuo mapper per ottenere tutti i dati (incluso profile e avatar)
            UserResponseDto userDto = UserMapper.toResponseDto(user);

            // ✅ Rigeneri token e scadenza
            String token = tokenProvider.generateToken(user);
            Date expiry = tokenProvider.getExpirationDateFromToken(token);

            // ✅ Costruisci oggetto con tutto
            UserSessionDto session = new UserSessionDto();
            session.setToken(token);
            session.setExpiry(expiry);
            session.setUser(userDto);

            return ResponseEntity.ok(session);

        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @Override
    public Long getUserIdFromEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return user.getId();
    }

    @Override
    public UserEntity findOrCreateFromGoogle(String email, String name) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    UserEntity user = new UserEntity();
                    user.setEmail(email);
                    user.setName(name);
                    user.setProvider("GOOGLE");
                    user.setRole(Role.USER); // default
                    user.setAccountStatus(AccountStatus.ACTIVE); // default
                    return userRepository.save(user);
                });
    }
}
