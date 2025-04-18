package com.ortoroverbasso.ortorovebasso.dto.shipping;

public class ShippingCountryResponseDto {
    private String isoCountry;

    // Default constructor
    public ShippingCountryResponseDto() {
    }

    // All-args constructor
    public ShippingCountryResponseDto(String isoCountry) {
        this.isoCountry = isoCountry;
    }

    // Getters and Setters
    public String getIsoCountry() {
        return isoCountry;
    }

    public void setIsoCountry(String isoCountry) {
        this.isoCountry = isoCountry;
    }
}
