package com.ortoroverbasso.ortorovebasso.entity.user;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
    ADMIN,
    USER,
    CONTRIBUTOR;

    public List<SimpleGrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.name()));
    }

    public static Role fromString(String role) {
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (Exception e) {
            return Role.USER; // Default role
        }
    }
}
