package com.ortoroverbasso.ortorovebasso.dto.user;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserSecurityRequestDto {

    @Schema(description = "Unique identifier of the security record.")
    private Long id;

    @Schema(description = "One-time password secret.")
    private String otpSecret;

    @Schema(description = "Whether multi-factor authentication is enabled.")
    private boolean mfaEnabled;

    @Schema(description = "Recovery keys for account recovery, stored as a JSON string.")
    private String recoveryKeys;

    @Schema(description = "Timestamp of the last login.")
    private LocalDateTime lastLoginAt;

    @Schema(description = "IP address of the last login.")
    private String lastLoginIp;

    // Default constructor
    public UserSecurityRequestDto() {
    }

    // All-args constructor
    public UserSecurityRequestDto(Long id, String otpSecret, boolean mfaEnabled, String recoveryKeys,
            LocalDateTime lastLoginAt, String lastLoginIp) {
        this.id = id;
        this.otpSecret = otpSecret;
        this.mfaEnabled = mfaEnabled;
        this.recoveryKeys = recoveryKeys;
        this.lastLoginAt = lastLoginAt;
        this.lastLoginIp = lastLoginIp;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOtpSecret() {
        return otpSecret;
    }

    public void setOtpSecret(String otpSecret) {
        this.otpSecret = otpSecret;
    }

    public boolean isMfaEnabled() {
        return mfaEnabled;
    }

    public void setMfaEnabled(boolean mfaEnabled) {
        this.mfaEnabled = mfaEnabled;
    }

    public String getRecoveryKeys() {
        return recoveryKeys;
    }

    public void setRecoveryKeys(String recoveryKeys) {
        this.recoveryKeys = recoveryKeys;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }
}
