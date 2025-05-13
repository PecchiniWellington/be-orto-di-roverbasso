package com.ortoroverbasso.ortorovebasso.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserPreferencesRequestDto {

    @Schema(description = "Unique identifier of the preferences.")
    private Long id;

    @Schema(description = "Whether email notifications are enabled.")
    private boolean emailNotificationsEnabled;

    @Schema(description = "Whether push notifications are enabled.")
    private boolean pushNotificationsEnabled;

    @Schema(description = "User's preferred language.")
    private String preferredLanguage;

    @Schema(description = "Privacy settings as a JSON string.")
    private String privacySettings;

    // Default constructor
    public UserPreferencesRequestDto() {
    }

    // All-args constructor
    public UserPreferencesRequestDto(Long id, boolean emailNotificationsEnabled,
            boolean pushNotificationsEnabled, String preferredLanguage,
            String privacySettings) {
        this.id = id;
        this.emailNotificationsEnabled = emailNotificationsEnabled;
        this.pushNotificationsEnabled = pushNotificationsEnabled;
        this.preferredLanguage = preferredLanguage;
        this.privacySettings = privacySettings;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEmailNotificationsEnabled() {
        return emailNotificationsEnabled;
    }

    public void setEmailNotificationsEnabled(boolean emailNotificationsEnabled) {
        this.emailNotificationsEnabled = emailNotificationsEnabled;
    }

    public boolean isPushNotificationsEnabled() {
        return pushNotificationsEnabled;
    }

    public void setPushNotificationsEnabled(boolean pushNotificationsEnabled) {
        this.pushNotificationsEnabled = pushNotificationsEnabled;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getPrivacySettings() {
        return privacySettings;
    }

    public void setPrivacySettings(String privacySettings) {
        this.privacySettings = privacySettings;
    }
}
