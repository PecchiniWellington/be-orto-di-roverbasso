package com.ortoroverbasso.ortorovebasso.dto.user;

import java.util.List;
import java.util.Map;

import com.ortoroverbasso.ortorovebasso.entity.user.Role;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserRequestDto {

    @Schema(description = "Unique identifier of the user.")
    private Long id;

    @Schema(description = "Name of the user.")
    private String name;

    @Schema(description = "Email address of the user.")
    private String email;

    @Schema(description = "Password of the user.")
    private String password;

    @Schema(description = "Role of the user (ADMIN, USER, CONTRIBUTOR).")
    private Role role;

    @Schema(description = "Authentication token of the user.")
    private String token;

    @Schema(description = "Expiration date of the authentication token.")
    private String tokenExpirationDate;

    @Schema(description = "Creation timestamp of the user record.")
    private String createdAt;

    @Schema(description = "Last update timestamp of the user record.")
    private String updatedAt;

    @Schema(description = "Deletion timestamp of the user record.")
    private String deletedAt;

    @Schema(description = "Last login timestamp of the user.")
    private String lastLoginAt;

    @Schema(description = "IP address of the user's last login.")
    private String lastLoginIp;

    @Schema(description = "User agent of the user's last login.")
    private String lastLoginUserAgent;

    @Schema(description = "Location of the user's last login.")
    private String lastLoginLocation;

    @Schema(description = "Device used for the user's last login.")
    private String lastLoginDevice;

    @Schema(description = "Browser used for the user's last login.")
    private String lastLoginBrowser;

    @Schema(description = "Full name of the user.")
    private String fullName;

    @Schema(description = "Phone number of the user.")
    private String phoneNumber;

    @Schema(description = "Address of the user.")
    private String address;

    @Schema(description = "Hashed password of the user.")
    private String passwordHash;

    @Schema(description = "Indicates if two-factor authentication is enabled.")
    private Boolean twoFactorAuthEnabled;

    @Schema(description = "Number of failed login attempts.")
    private Integer failedLoginAttempts;

    @Schema(description = "Timestamp of the last password change.")
    private String lastPasswordChange;

    @Schema(description = "Preferred language of the user.")
    private String language;

    @Schema(description = "Time zone of the user.")
    private String timeZone;

    @Schema(description = "Indicates if the user prefers a dark theme.")
    private Boolean themePreferenceDark;

    @Schema(description = "Notification preferences of the user.")
    private Map<String, Boolean> notificationPreferences;

    @Schema(description = "Account status of the user.")
    private String accountStatus;

    @Schema(description = "Account type of the user.")
    private String accountType;

    @Schema(description = "Subscription status of the user.")
    private String subscriptionStatus;

    @Schema(description = "Expiration date of the user's account.")
    private String accountExpiration;

    @Schema(description = "Referral code of the user.")
    private String referralCode;

    @Schema(description = "Activity log of the user.")
    private List<String> activityLog;

    @Schema(description = "Purchase history of the user.")
    private List<String> purchaseHistory;

    @Schema(description = "Twitter handle of the user.")
    private String twitterHandle;

    @Schema(description = "LinkedIn handle of the user.")
    private String linkedinHandle;

    @Schema(description = "Indicates if the user's email is verified.")
    private Boolean emailVerified;

    @Schema(description = "Email verification token of the user.")
    private String emailVerificationToken;

    @Schema(description = "Recovery token of the user.")
    private String recoveryToken;

    @Schema(description = "Expiration date of the recovery token.")
    private String recoveryTokenExpiration;

    @Schema(description = "Country of the user.")
    private String country;

    @Schema(description = "Region of the user.")
    private String region;

    @Schema(description = "Device history of the user.")
    private List<String> deviceHistory;

    // Default constructor
    public UserRequestDto() {
    }

    // All-args constructor
    public UserRequestDto(
            Long id,
            String name,
            String email,
            String password,
            Role role,
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
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
