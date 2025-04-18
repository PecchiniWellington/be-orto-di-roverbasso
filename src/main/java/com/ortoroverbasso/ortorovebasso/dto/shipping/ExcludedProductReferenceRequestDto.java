package com.ortoroverbasso.ortorovebasso.dto.shipping;

public class ExcludedProductReferenceRequestDto {
    private String reference;

    // Default constructor
    public ExcludedProductReferenceRequestDto() {
    }

    // All-args constructor
    public ExcludedProductReferenceRequestDto(String reference) {
        this.reference = reference;
    }

    // Getters and Setters
    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
