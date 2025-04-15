package com.ortoroverbasso.ortorovebasso.entity.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;

@Table(name = "users")
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String token;
    private String tokenExpirationDate;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private String lastLoginAt;
    private String lastLoginIp;
    private String lastLoginUserAgent;
    private String lastLoginLocation;
    private String lastLoginDevice;
    private String lastLoginBrowser;

    // Dati anagrafici
    private String fullName;
    private String phoneNumber;
    private String address;

    // Sicurezza e autenticazione
    private String passwordHash;
    private Boolean twoFactorAuthEnabled;
    private Integer failedLoginAttempts;
    private String lastPasswordChange;

    // Preferenze
    private String language;
    private String timeZone;
    private Boolean themePreferenceDark;

    @ElementCollection
    @CollectionTable(name = "user_notification_preferences", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "preference_key")
    @Column(name = "preference_value")
    private Map<String, Boolean> notificationPreferences = new HashMap<>();

    // Stato dell'account
    private String accountStatus;
    private String accountType;
    private String subscriptionStatus;
    private String accountExpiration;

    // Tracciamento
    private String referralCode;
    private List<String> activityLog;
    private List<String> purchaseHistory;

    // Integrazione social
    private String twitterHandle;
    private String linkedinHandle;

    // Verifica dell'email
    private Boolean emailVerified;
    private String emailVerificationToken;

    // Recupero account
    private String recoveryToken;
    private String recoveryTokenExpiration;

    // Geolocalizzazione
    private String country;
    private String region;
    private List<String> deviceHistory;

    // Constructor
    public UserEntity() {
    }

    public UserEntity(
            Long id,
            String name,
            String email,
            String password,
            String role,
            String token,
            String tokenExpirationDate,
            String createdAt,
            String updatedAt,
            String deletedAt,
            String lastLoginAt,
            String lastLoginIp,
            String lastLoginUserAgent,
            String lastLoginLocation,
            String lastLoginDevice,
            String lastLoginBrowser,
            String fullName,
            String phoneNumber,
            String address,
            String passwordHash,
            Boolean twoFactorAuthEnabled,
            Integer failedLoginAttempts,
            String lastPasswordChange,
            String language,
            String timeZone,
            Boolean themePreferenceDark,
            Map<String, Boolean> notificationPreferences,
            String accountStatus,
            String accountType,
            String subscriptionStatus,
            String accountExpiration,
            String referralCode,
            List<String> activityLog,
            List<String> purchaseHistory,
            String twitterHandle,
            String linkedinHandle,
            Boolean emailVerified,
            String emailVerificationToken,
            String recoveryToken,
            String recoveryTokenExpiration,
            String country,
            String region,
            List<String> deviceHistory) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.token = token;
        this.tokenExpirationDate = tokenExpirationDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.lastLoginAt = lastLoginAt;
        this.lastLoginIp = lastLoginIp;
        this.lastLoginUserAgent = lastLoginUserAgent;
        this.lastLoginLocation = lastLoginLocation;
        this.lastLoginDevice = lastLoginDevice;
        this.lastLoginBrowser = lastLoginBrowser;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.passwordHash = passwordHash;
        this.twoFactorAuthEnabled = twoFactorAuthEnabled;
        this.failedLoginAttempts = failedLoginAttempts;
        this.lastPasswordChange = lastPasswordChange;
        this.language = language;
        this.timeZone = timeZone;
        this.themePreferenceDark = themePreferenceDark;
        this.notificationPreferences = notificationPreferences;
        this.accountStatus = accountStatus;
        this.accountType = accountType;
        this.subscriptionStatus = subscriptionStatus;
        this.accountExpiration = accountExpiration;
        this.referralCode = referralCode;
        this.activityLog = activityLog;
        this.purchaseHistory = purchaseHistory;
        this.twitterHandle = twitterHandle;
        this.linkedinHandle = linkedinHandle;
        this.emailVerified = emailVerified;
        this.emailVerificationToken = emailVerificationToken;
        this.recoveryToken = recoveryToken;
        this.recoveryTokenExpiration = recoveryTokenExpiration;
        this.country = country;
        this.region = region;
        this.deviceHistory = deviceHistory;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenExpirationDate() {
        return tokenExpirationDate;
    }

    public void setTokenExpirationDate(String tokenExpirationDate) {
        this.tokenExpirationDate = tokenExpirationDate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(String lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getLastLoginUserAgent() {
        return lastLoginUserAgent;
    }

    public void setLastLoginUserAgent(String lastLoginUserAgent) {
        this.lastLoginUserAgent = lastLoginUserAgent;
    }

    public String getLastLoginLocation() {
        return lastLoginLocation;
    }

    public void setLastLoginLocation(String lastLoginLocation) {
        this.lastLoginLocation = lastLoginLocation;
    }

    public String getLastLoginDevice() {
        return lastLoginDevice;
    }

    public void setLastLoginDevice(String lastLoginDevice) {
        this.lastLoginDevice = lastLoginDevice;
    }

    public String getLastLoginBrowser() {
        return lastLoginBrowser;
    }

    public void setLastLoginBrowser(String lastLoginBrowser) {
        this.lastLoginBrowser = lastLoginBrowser;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Boolean getTwoFactorAuthEnabled() {
        return twoFactorAuthEnabled;
    }

    public void setTwoFactorAuthEnabled(Boolean twoFactorAuthEnabled) {
        this.twoFactorAuthEnabled = twoFactorAuthEnabled;
    }

    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public String getLastPasswordChange() {
        return lastPasswordChange;
    }

    public void setLastPasswordChange(String lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Boolean getThemePreferenceDark() {
        return themePreferenceDark;
    }

    public void setThemePreferenceDark(Boolean themePreferenceDark) {
        this.themePreferenceDark = themePreferenceDark;
    }

    public Map<String, Boolean> getNotificationPreferences() {
        return notificationPreferences;
    }

    public void setNotificationPreferences(Map<String, Boolean> notificationPreferences) {
        this.notificationPreferences = notificationPreferences;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public String getAccountExpiration() {
        return accountExpiration;
    }

    public void setAccountExpiration(String accountExpiration) {
        this.accountExpiration = accountExpiration;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public List<String> getActivityLog() {
        return activityLog;
    }

    public void setActivityLog(List<String> activityLog) {
        this.activityLog = activityLog;
    }

    public List<String> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(List<String> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    public String getLinkedinHandle() {
        return linkedinHandle;
    }

    public void setLinkedinHandle(String linkedinHandle) {
        this.linkedinHandle = linkedinHandle;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    public String getRecoveryToken() {
        return recoveryToken;
    }

    public void setRecoveryToken(String recoveryToken) {
        this.recoveryToken = recoveryToken;
    }

    public String getRecoveryTokenExpiration() {
        return recoveryTokenExpiration;
    }

    public void setRecoveryTokenExpiration(String recoveryTokenExpiration) {
        this.recoveryTokenExpiration = recoveryTokenExpiration;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<String> getDeviceHistory() {
        return deviceHistory;
    }

    public void setDeviceHistory(List<String> deviceHistory) {
        this.deviceHistory = deviceHistory;
    }

}
