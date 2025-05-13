package com.ortoroverbasso.ortorovebasso.dto.user;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserSecurityResponseDto {

    @Schema(description = "Unique identifier of the security record.")
    private Long id;

    @Schema(description = "Whether multi-factor authentication is enabled.")
    private boolean mfaEnabled;

    @Schema(description = "Timestamp of the last login.")
    private LocalDateTime lastLoginAt;

    @Schema(description = "IP address of the last login.")
    private String lastLoginIp;

    // Note: We intentionally don't include OTP secrets or recovery keys in response
    // DTOs for security reasons

    // Default constructor
    public UserSecurityResponseDto() {
    }

    // All-args constructor
    public UserSecurityResponseDto(Long id, boolean mfaEnabled,
            LocalDateTime lastLoginAt, String lastLoginIp) {
        this.id = id;
        this.mfaEnabled = mfaEnabled;
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

    public boolean isMfaEnabled() {
        return mfaEnabled;
    }

    public void setMfaEnabled(boolean mfaEnabled) {
        this.mfaEnabled = mfaEnabled;
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
