package com.ortoroverbasso.ortorovebasso.entity.user;

import java.util.ArrayList;
import java.util.List;

import com.ortoroverbasso.ortorovebasso.entity.cart.CartEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.user_address.UserAddressEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.user_preferences.UserPreferencesEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.user_profile.UserProfileEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.user_security.UserSecurityEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private boolean emailVerified = false;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    private String provider;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserProfileEntity profile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserPreferencesEntity preferences;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserSecurityEntity security;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAddressEntity> addresses = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private CartEntity cart;

    // Constructors
    public UserEntity() {
    }

    public UserEntity(Long id) {
        this.id = id;
    }

    public UserEntity(
            Long id,
            String name,
            String email,
            String password,
            Role role,
            AccountStatus accountStatus,
            UserProfileEntity profile,
            UserPreferencesEntity preferences,
            UserSecurityEntity security,
            List<UserAddressEntity> addresses,
            String provider,
            CartEntity cart,
            boolean emailVerified) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.accountStatus = accountStatus;
        this.profile = profile;
        this.preferences = preferences;
        this.security = security;
        this.addresses = addresses;
        this.provider = provider;
        this.cart = cart;
        this.emailVerified = emailVerified;

    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public UserProfileEntity getProfile() {
        return profile;
    }

    public void setProfile(UserProfileEntity profile) {
        this.profile = profile;
    }

    public UserPreferencesEntity getPreferences() {
        return preferences;
    }

    public void setPreferences(UserPreferencesEntity preferences) {
        this.preferences = preferences;
    }

    public UserSecurityEntity getSecurity() {
        return security;
    }

    public void setSecurity(UserSecurityEntity security) {
        this.security = security;
    }

    public List<UserAddressEntity> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<UserAddressEntity> addresses) {
        this.addresses = addresses;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }

    public CartEntity getCart() {
        return cart;
    }

    public void setCart(CartEntity cart) {
        this.cart = cart;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

}
