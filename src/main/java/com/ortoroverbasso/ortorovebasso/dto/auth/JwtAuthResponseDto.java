package com.ortoroverbasso.ortorovebasso.dto.auth;

import java.util.Date;

public class JwtAuthResponseDto {
    private String accessToken;
    private String tokenType = "Bearer";
    private Long userId;
    private String username;
    private String role;
    private Date expirationDate;

    public JwtAuthResponseDto(String accessToken, Long userId, String username, String role, Date expirationDate) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.expirationDate = expirationDate;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
