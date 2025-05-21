package com.ortoroverbasso.ortorovebasso.service.auth.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.entity.user.AccountStatus;
import com.ortoroverbasso.ortorovebasso.entity.user.Role;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.repository.user.UserRepository;
import com.ortoroverbasso.ortorovebasso.security.JwtTokenProvider;
import com.ortoroverbasso.ortorovebasso.service.auth.IOAuth2Service;

@Service
public class OAuth2Service implements IOAuth2Service {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public OAuth2Service(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String handleOAuth2User(Map<String, Object> attributes, String provider) {
        String rawEmail = (String) attributes.get("email");
        String rawName = (String) attributes.get("name");

        if (rawEmail == null || rawEmail.isBlank()) {
            String id = String.valueOf(attributes.get("id"));
            rawEmail = "user_" + id + "@social.local";
        }

        if (rawName == null || rawName.isBlank()) {
            rawName = "Utente Social";
        }

        final String email = rawEmail;
        final String name = rawName;

        UserEntity user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    UserEntity newUser = new UserEntity();
                    newUser.setEmail(email);
                    newUser.setName(name);
                    newUser.setPassword("");
                    newUser.setRole(Role.USER);
                    newUser.setAccountStatus(AccountStatus.ACTIVE);
                    newUser.setProvider(provider);
                    return userRepository.save(newUser);
                });

        return jwtTokenProvider.generateToken(user);
    }

}
