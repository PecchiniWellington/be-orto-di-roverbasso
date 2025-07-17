package com.ortoroverbasso.ortorovebasso.dto.password_reset;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class PasswordChangeRequestDto {

    @NotBlank(message = "La vecchia password è obbligatoria")
    private String oldPassword;

    @NotBlank(message = "La nuova password è obbligatoria")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_\\-+=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$", message = "La password deve contenere almeno 8 caratteri, una maiuscola, una minuscola, un numero e un simbolo speciale")
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
