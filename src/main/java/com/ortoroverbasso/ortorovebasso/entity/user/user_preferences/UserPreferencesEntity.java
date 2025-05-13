package com.ortoroverbasso.ortorovebasso.entity.user.user_preferences;

import java.util.Map;

import org.hibernate.annotations.Type;

import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.vladmihalcea.hibernate.type.json.JsonType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_preferences")
public class UserPreferencesEntity {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_preferences_user"))
    private UserEntity user;

    private boolean emailNotificationsEnabled;

    private boolean pushNotificationsEnabled;

    private String preferredLanguage;

    @Type(JsonType.class)
    @Column(name = "privacy_settings", columnDefinition = "jsonb")
    private Map<String, Object> privacySettings;

    public UserPreferencesEntity() {
    }

    public UserPreferencesEntity(Long id, UserEntity user, boolean emailNotificationsEnabled,
            boolean pushNotificationsEnabled, String preferredLanguage, Map<String, Object> privacySettings) {
        this.id = id;
        this.user = user;
        this.emailNotificationsEnabled = emailNotificationsEnabled;
        this.pushNotificationsEnabled = pushNotificationsEnabled;
        this.preferredLanguage = preferredLanguage;
        this.privacySettings = privacySettings;
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

    public Map<String, Object> getPrivacySettings() {
        return privacySettings;
    }

    public void setPrivacySettings(Map<String, Object> privacySettings) {
        this.privacySettings = privacySettings;
    }
}
