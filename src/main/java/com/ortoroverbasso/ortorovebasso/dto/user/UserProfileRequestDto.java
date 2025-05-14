package com.ortoroverbasso.ortorovebasso.dto.user;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserProfileRequestDto {

    @Schema(description = "Unique identifier of the profile.")
    private Long id;

    @Schema(description = "User's biography or about text.")
    private String bio;

    @Schema(description = "User's phone number.")
    private String phoneNumber;

    @Schema(description = "User's date of birth.")
    private LocalDate birthDate;

    @Schema(description = "User's gender.")
    private Gender gender;

    @Schema(description = "ID of the user's avatar image (ImagesDetailEntity.id).")
    private Long avatarId;

    // Costruttori
    public UserProfileRequestDto() {
    }

    public UserProfileRequestDto(Long id, String bio, String phoneNumber, LocalDate birthDate, Gender gender,
            Long avatarId) {
        this.id = id;
        this.bio = bio;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.gender = gender;
        this.avatarId = avatarId;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
