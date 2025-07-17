package com.ortoroverbasso.ortorovebasso.dto.password_reset;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class PasswordResetRequestDto {
    private String token;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    private String newPassword;

    public PasswordResetRequestDto() {
    }

    public PasswordResetRequestDto(String token, String email, String newPassword) {
        this.token = token;
        this.email = email;
        this.newPassword = newPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
