package com.ortoroverbasso.ortorovebasso.service.password_reset.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.entity.password_reset.PasswordResetTokenEntity;
import com.ortoroverbasso.ortorovebasso.repository.password_reset.PasswordResetTokenRepository;
import com.ortoroverbasso.ortorovebasso.service.email.IEmailService;
import com.ortoroverbasso.ortorovebasso.service.password_reset.IPasswordResetService;

import jakarta.transaction.Transactional;

@Service
public class PasswordResetServiceImpl implements IPasswordResetService {

    private final PasswordResetTokenRepository tokenRepository;
    private final IEmailService emailService;

    @Value("${app.frontend.reset-url}") // es: http://localhost:3000/reset-password
    private String frontendResetUrl;

    public PasswordResetServiceImpl(PasswordResetTokenRepository tokenRepository, IEmailService emailService) {
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    @Override
    public void sendResetToken(String email) {
        String token = UUID.randomUUID().toString();

        PasswordResetTokenEntity entity = new PasswordResetTokenEntity();
        entity.setEmail(email);
        entity.setToken(token);
        entity.setExpirationDate(LocalDateTime.now().plusHours(1));
        tokenRepository.save(entity);

        String link = frontendResetUrl + "?token=" + token;
        String subject = "Recupero password";
        String body = "Clicca sul link per reimpostare la password: " + link;

        emailService.send(email, subject, body);
    }

    @Transactional
    @Override
    public boolean isValidToken(String token) {
        return tokenRepository.findByToken(token)
                .filter(t -> t.getExpirationDate().isAfter(LocalDateTime.now()))
                .isPresent();
    }

    @Transactional
    @Override
    public void invalidateToken(String token) {
        tokenRepository.deleteByToken(token);
    }
}
