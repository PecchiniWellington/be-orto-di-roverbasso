package com.ortoroverbasso.ortorovebasso.service.password_reset;

public interface IPasswordResetService {
    void sendResetToken(String email);

    boolean isValidToken(String token);

    void invalidateToken(String token);
}
