package com.ortoroverbasso.ortorovebasso.dto.user;

import java.util.Date;

public class UserSessionDto {
    private String token;
    private Date expiry;
    private UserResponseDto user;

    public UserSessionDto() {
    }

    public UserSessionDto(String token, Date expiry, UserResponseDto user) {
        this.token = token;
        this.expiry = expiry;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public UserResponseDto getUser() {
        return user;
    }

    public void setUser(UserResponseDto user) {
        this.user = user;
    }
}
