package com.ortoroverbasso.ortorovebasso.service.email;

import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;

public interface IEmailVerificationService {
    void sendVerificationToken(UserEntity user);

    boolean verifyEmail(String token);

    String buildEmailVerification(String name, String verificationUrl);

}
