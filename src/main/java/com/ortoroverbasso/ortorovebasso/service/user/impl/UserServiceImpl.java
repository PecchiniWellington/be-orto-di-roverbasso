package com.ortoroverbasso.ortorovebasso.service.user.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.ortoroverbasso.ortorovebasso.dto.user.UserRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserSessionDto;
import com.ortoroverbasso.ortorovebasso.entity.images.ImagesDetailEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.AccountStatus;
import com.ortoroverbasso.ortorovebasso.entity.user.Role;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.user_preferences.UserPreferencesEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.user_profile.UserProfileEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.user_security.UserSecurityEntity;
import com.ortoroverbasso.ortorovebasso.exception.UserAlreadyExistsException;
import com.ortoroverbasso.ortorovebasso.exception.UserNotFoundException;
import com.ortoroverbasso.ortorovebasso.mapper.user.UserMapper;
import com.ortoroverbasso.ortorovebasso.repository.email_verification.EmailVerificationTokenRepository;
import com.ortoroverbasso.ortorovebasso.repository.user.UserRepository;
import com.ortoroverbasso.ortorovebasso.security.JwtTokenProvider;
import com.ortoroverbasso.ortorovebasso.service.images.IImagesDetailService;
import com.ortoroverbasso.ortorovebasso.service.user.IUserService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private IImagesDetailService imageDetailService;
    @Autowired
    private EmailVerificationTokenRepository emailVerificationTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public List<UserResponseDto> getAllUsers() {
        List<UserEntity> usersEntity = userRepository.findAll();
        return usersEntity.stream().map(UserMapper::toResponseDto).toList();
    }

    @Transactional
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

        if (userRequest.getPassword() != null && !userRequest.getPassword().isBlank()) {
            userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        // Set default role and status if not provided
        if (userEntity.getRole() == null) {
            userEntity.setRole(Role.USER);
        }
        userEntity.setAccountStatus(AccountStatus.ACTIVE);

        // Crea entità collegate
        UserProfileEntity profile = new UserProfileEntity();
        profile.setUser(userEntity);

        UserPreferencesEntity preferences = new UserPreferencesEntity();
        preferences.setUser(userEntity);

        UserSecurityEntity security = new UserSecurityEntity();
        security.setUser(userEntity);

        // Associa tutto all’utente
        userEntity.setProfile(profile);
        userEntity.setPreferences(preferences);
        userEntity.setSecurity(security);

        // Salva utente (cascading salverà anche le altre entità se configurato)
        userEntity = userRepository.save(userEntity);

        UserResponseDto responseDto = UserMapper.toResponseDto(userEntity);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(Long id, @Valid @RequestBody UserRequestDto userDto) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Nome
        if (userDto.getName() != null && !userDto.getName().isBlank()) {
            userEntity.setName(userDto.getName());
        }
        // Cognome
        if (userDto.getLastName() != null && !userDto.getLastName().isBlank()) {
            userEntity.setLastName(userDto.getLastName());
        }

        /* CONTROLLO EMAIL */
        if (userDto.getEmail() != null && !userDto.getEmail().isBlank()) {
            if (!userDto.getEmail().equals(userEntity.getEmail())) {
                boolean emailExists = userRepository.existsByEmail(userDto.getEmail());
                if (emailExists) {
                    throw new UserAlreadyExistsException("Email già registrata.");
                }
                userEntity.setEmail(userDto.getEmail());
            }
        }
        // Email
        if (userDto.getEmail() != null && !userDto.getEmail().isBlank()) {
            userEntity.setEmail(userDto.getEmail());
        }

        // Password
        if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
            userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        // Role
        if (userDto.getRole() != null) {
            userEntity.setRole(userDto.getRole());
        }

        // Account Status
        if (userDto.getAccountStatus() != null) {
            userEntity.setAccountStatus(userDto.getAccountStatus());
        }

        // Provider
        if (userDto.getProvider() != null) {
            userEntity.setProvider(userDto.getProvider());
        }

        /*
         * if (userDto.getProfile() != null) {
         * userEntity.setProfile(userProfileMapper.toEntity(userDto.getProfile(),
         * userEntity.getProfile()));
         * }
         *
         *
         * if (userDto.getPreferences() != null) {
         * userEntity.setPreferences(
         * userPreferencesMapper.toEntity(userDto.getPreferences(),
         * userEntity.getPreferences()));
         * }
         *
         *
         * if (userDto.getSecurity() != null) {
         * userEntity.setSecurity(userSecurityMapper.toEntity(userDto.getSecurity(),
         * userEntity.getSecurity()));
         * }
         *
         *
         * if (userDto.getAddresses() != null) {
         * List<UserAddressEntity> updatedAddresses = userDto.getAddresses().stream()
         * .map(addr -> userAddressMapper.toEntity(addr))
         * .collect(Collectors.toList());
         *
         * userEntity.setAddresses(updatedAddresses);
         * }
         */

        UserEntity updated = userRepository.save(userEntity);
        return UserMapper.toResponseDto(updated);
    }

    @Override
    @Transactional
    public UserResponseDto deleteUser(Long id) {
        // 1️⃣ Recupera l'utente o lancia eccezione se non esiste
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // 2️⃣ Elimina token di verifica email associati
        emailVerificationTokenRepository.deleteAllByUser_Id(id);

        // 3️⃣ Elimina immagine avatar se presente
        UserProfileEntity profile = userEntity.getProfile();
        if (profile != null) {
            ImagesDetailEntity avatar = profile.getAvatar();
            if (avatar != null && avatar.getFileId() != null && !avatar.getFileId().isBlank()) {
                imageDetailService.deleteImage(avatar.getId()); // elimina da B2 + DB
            }
        }

        // 4️⃣ Elimina l'utente
        userRepository.delete(userEntity);

        // 5️⃣ Mappa e restituisci la risposta
        UserResponseDto responseDto = UserMapper.toResponseDto(userEntity);
        responseDto.setMessage(
                "User with ID " + userEntity.getId() + " and name " + userEntity.getName() + " has been deleted.");
        return responseDto;
    }

    @Transactional
    @Override
    public UserResponseDto getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return UserMapper.toResponseDto(userEntity);
    }

    @Transactional
    @Override
    public ResponseEntity<?> getCurrentAuthenticatedUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }

            String username;

            if (authentication.getPrincipal() instanceof OAuth2User oauth2User) {
                username = (String) oauth2User.getAttributes().get("email");
            } else {
                username = authentication.getName();
            }

            if (username == null || username.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username in authentication");
            }

            UserEntity user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found with email: " + username));

            UserResponseDto userDto = UserMapper.toResponseDto(user);

            String token = tokenProvider.generateToken(user);
            Date expiry = tokenProvider.getExpirationDateFromToken(token);

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

    @Transactional
    @Override
    public Long getUserIdFromEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return user.getId();
    }

    @Override
    public UserEntity findOrCreateFromGoogle(String email, String name, String pictureUrl) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    UserEntity user = new UserEntity();
                    user.setEmail(email);
                    user.setName(name);
                    user.setProvider("GOOGLE");
                    user.setRole(Role.USER);
                    user.setAccountStatus(AccountStatus.ACTIVE);

                    UserProfileEntity profile = new UserProfileEntity();
                    profile.setUser(user);

                    // Solo se è presente una picture valida e non c’è già un avatar
                    if (pictureUrl != null && !pictureUrl.isBlank()) {
                        ImagesDetailEntity avatar = new ImagesDetailEntity();
                        avatar.setUrl(pictureUrl);
                        avatar.setName("avatar_google_oauth.jpg");
                        profile.setAvatar(avatar);
                    }

                    user.setProfile(profile);

                    return userRepository.save(user);
                });
    }

    @Transactional
    @Override
    public ResponseEntity<?> getCurrentAuthenticatedUserByEmail(String email) {
        try {
            UserEntity user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

            UserResponseDto userDto = UserMapper.toResponseDto(user);
            String token = tokenProvider.generateToken(user);
            Date expiry = tokenProvider.getExpirationDateFromToken(token);

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

    @Transactional
    @Override
    public void assignProviderAvatarIfMissing(UserEntity user, String pictureUrl) {
        System.out.println("👉 Metodo chiamato con pictureUrl = " + pictureUrl);

        // Rendi l'utente gestito dalla sessione
        UserEntity managedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        UserProfileEntity profile = managedUser.getProfile();

        if (profile != null) {
            ImagesDetailEntity currentAvatar = profile.getAvatar();

            if (currentAvatar != null) {
                Hibernate.initialize(currentAvatar);
            }

            boolean shouldUpload = (currentAvatar == null)
                    || (currentAvatar.getFileId() == null || currentAvatar.getFileId().isBlank());

            if (shouldUpload && pictureUrl != null && !pictureUrl.isBlank()) {
                System.out.println("🎯 Inizio upload immagine da URL");

                ImagesDetailEntity avatar = imageDetailService.uploadFromUrl(pictureUrl, "avatar_provider");

                profile.setAvatar(avatar);
                userRepository.save(managedUser);
            } else {
                System.out.println("⛔️ Condizione non soddisfatta per l'upload avatar");
            }
        } else {
            System.out.println("⛔️ Profilo utente nullo");
        }
    }

    @Override
    @Transactional
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato con email: " + email));
    }

}
