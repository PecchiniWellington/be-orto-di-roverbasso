package com.ortoroverbasso.ortorovebasso.dto.user;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.entity.user.AccountStatus;
import com.ortoroverbasso.ortorovebasso.entity.user.Role;
import com.ortoroverbasso.ortorovebasso.service.validation.ValidationGroups;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserRequestDto {

    private Long id;
    private String name;
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "La password è obbligatoria", groups = ValidationGroups.OnCreate.class)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_\\-+=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$", message = "La password deve contenere almeno 8 caratteri, una maiuscola, una minuscola, un numero e un simbolo speciale")
    private String password;
    private Role role;
    private AccountStatus accountStatus;

    private UserProfileRequestDto profile;
    private UserPreferencesRequestDto preferences;
    private UserSecurityRequestDto security;
    private List<UserAddressRequestDto> addresses;
    private String provider;

    public UserRequestDto() {
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

    public UserProfileRequestDto getProfile() {
        return profile;
    }

    public void setProfile(UserProfileRequestDto profile) {
        this.profile = profile;
    }

    public UserPreferencesRequestDto getPreferences() {
        return preferences;
    }

    public void setPreferences(UserPreferencesRequestDto preferences) {
        this.preferences = preferences;
    }

    public UserSecurityRequestDto getSecurity() {
        return security;
    }

    public void setSecurity(UserSecurityRequestDto security) {
        this.security = security;
    }

    public List<UserAddressRequestDto> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<UserAddressRequestDto> addresses) {
        this.addresses = addresses;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
