package com.ortoroverbasso.ortorovebasso.entity.user.user_profile;

import java.time.LocalDate;

import com.ortoroverbasso.ortorovebasso.dto.user.Gender;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_profiles")
public class UserProfileEntity {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String phoneNumber;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Long avatarId;

    // Getters and setters

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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Long avatarId) {
        this.avatarId = avatarId;
    }

    public UserProfileEntity() {
    }

    public UserProfileEntity(Long id, UserEntity user, String bio, String phoneNumber, LocalDate birthDate,
            Gender gender, Long avatarId) {
        this.id = id;
        this.user = user;
        this.bio = bio;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.gender = gender;
        this.avatarId = avatarId;
    }
}
