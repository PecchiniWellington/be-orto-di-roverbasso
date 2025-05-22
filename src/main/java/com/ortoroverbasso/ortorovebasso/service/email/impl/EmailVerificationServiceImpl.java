package com.ortoroverbasso.ortorovebasso.service.email.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.ortoroverbasso.ortorovebasso.entity.email_verification.EmailVerificationTokenEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.repository.email_verification.EmailVerificationTokenRepository;
import com.ortoroverbasso.ortorovebasso.repository.user.UserRepository;
import com.ortoroverbasso.ortorovebasso.service.email.IEmailService;
import com.ortoroverbasso.ortorovebasso.service.email.IEmailVerificationService;

import jakarta.transaction.Transactional;

@Service
public class EmailVerificationServiceImpl implements IEmailVerificationService {
    @Autowired
    private EmailVerificationTokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IEmailService emailService;
    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${app.frontend.verify-url}") // es: http://localhost:3000/verifica-email
    private String frontendVerifyUrl;

    @Override
    public void sendVerificationToken(UserEntity user) {
        String token = UUID.randomUUID().toString();

        EmailVerificationTokenEntity entity = new EmailVerificationTokenEntity();
        entity.setUser(user);
        entity.setToken(token);
        entity.setExpirationDate(LocalDateTime.now().plusHours(24));
        tokenRepository.save(entity);

        String link = frontendVerifyUrl + "?token=" + token;
        String body = buildEmailVerification(user.getName(), link);
        emailService.send(user.getEmail(), "Verifica il tuo account", body);
    }

    @Override
    public String buildEmailVerification(String name, String verificationUrl) {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("verificationUrl", verificationUrl);
        return templateEngine.process("email-verification", context);
    }

    @Transactional
    @Override
    public boolean verifyEmail(String token) {
        return tokenRepository.findByToken(token)
                .filter(t -> t.getExpirationDate().isAfter(LocalDateTime.now()))
                .map(t -> {
                    UserEntity user = t.getUser();
                    user.setEmailVerified(true);
                    userRepository.save(user);
                    tokenRepository.deleteByToken(token);
                    return true;
                })
                .orElse(false);
    }
}
