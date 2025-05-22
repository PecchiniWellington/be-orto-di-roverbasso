package com.ortoroverbasso.ortorovebasso.dto.password_reset;

import jakarta.validation.constraints.NotBlank;

public class PasswordChangeRequestDto {
    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;

    // Getter e Setter
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
