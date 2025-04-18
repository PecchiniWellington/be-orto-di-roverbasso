package com.ortoroverbasso.ortorovebasso.dto.shipping;

public class ProductCountryRequestDto {

    private String reference; // riferimento del prodotto (ad esempio, "S12435678")
    private String countryIsoCode; // codice ISO del paese (ad esempio, "en")

    // Costruttore di default
    public ProductCountryRequestDto() {
    }

    // Costruttore con parametri
    public ProductCountryRequestDto(String reference, String countryIsoCode) {
        this.reference = reference;
        this.countryIsoCode = countryIsoCode;
    }

    // Getters and Setters
    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCountryIsoCode() {
        return countryIsoCode;
    }

    public void setCountryIsoCode(String countryIsoCode) {
        this.countryIsoCode = countryIsoCode;
    }
}
