package com.ortoroverbasso.ortorovebasso.entity.user.user_security;

import java.time.LocalDateTime;

import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_security")
public class UserSecurityEntity {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String otpSecret;

    private boolean mfaEnabled;

    @Column(columnDefinition = "JSONB")
    private String recoveryKeys;

    private LocalDateTime lastLoginAt;

    private String lastLoginIp;

    // Getters and setters

    public UserSecurityEntity() {
    }

    public UserSecurityEntity(Long id, UserEntity user, String otpSecret, boolean mfaEnabled, String recoveryKeys,
            LocalDateTime lastLoginAt, String lastLoginIp) {
        this.id = id;
        this.user = user;
        this.otpSecret = otpSecret;
        this.mfaEnabled = mfaEnabled;
        this.recoveryKeys = recoveryKeys;
        this.lastLoginAt = lastLoginAt;
        this.lastLoginIp = lastLoginIp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
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
