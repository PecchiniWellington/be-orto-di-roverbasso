package com.ortoroverbasso.ortorovebasso.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserAddressResponseDto {

    @Schema(description = "Unique identifier of the address.")
    private Long id;

    @Schema(description = "Street address including house/building number.")
    private String streetAddress;

    @Schema(description = "City name.")
    private String city;

    @Schema(description = "State, province, or region.")
    private String state;

    @Schema(description = "Postal or ZIP code.")
    private String postalCode;

    @Schema(description = "Country name.")
    private String country;

    @Schema(description = "Whether this is the user's primary address.")
    private boolean isPrimary;

    // Default constructor
    public UserAddressResponseDto() {
    }

    // All-args constructor
    public UserAddressResponseDto(Long id, String streetAddress, String city, String state,
            String postalCode, String country, boolean isPrimary) {
        this.id = id;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.isPrimary = isPrimary;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }
}
