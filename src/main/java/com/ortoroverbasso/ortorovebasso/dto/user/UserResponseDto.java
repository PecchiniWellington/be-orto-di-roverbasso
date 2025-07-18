package com.ortoroverbasso.ortorovebasso.dto.user;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.cart.CartResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.user.AccountStatus;
import com.ortoroverbasso.ortorovebasso.entity.user.Role;

public class UserResponseDto {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private Role role;
    private AccountStatus accountStatus;

    private UserProfileResponseDto profile;
    private UserPreferencesResponseDto preferences;
    private UserSecurityResponseDto security;
    private List<UserAddressResponseDto> addresses;
    private String provider;
    private CartResponseDto cart;
    private boolean emailVerified; // opzionale per feedback (es. "Email verificata")

    private String message; // opzionale per feedback (es. "Utente creato correttamente")

    public UserResponseDto() {
    }

    // Getters e Setters
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public UserProfileResponseDto getProfile() {
        return profile;
    }

    public void setProfile(UserProfileResponseDto profile) {
        this.profile = profile;
    }

    public UserPreferencesResponseDto getPreferences() {
        return preferences;
    }

    public void setPreferences(UserPreferencesResponseDto preferences) {
        this.preferences = preferences;
    }

    public UserSecurityResponseDto getSecurity() {
        return security;
    }

    public void setSecurity(UserSecurityResponseDto security) {
        this.security = security;
    }

    public List<UserAddressResponseDto> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<UserAddressResponseDto> addresses) {
        this.addresses = addresses;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public CartResponseDto getCart() {
        return cart;
    }

    public void setCart(CartResponseDto cart) {
        this.cart = cart;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

}
