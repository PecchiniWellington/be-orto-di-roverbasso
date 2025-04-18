package com.ortoroverbasso.ortorovebasso.dto.shipping;

public class ExcludedProductReferenceResponseDto {
    private String reference;

    // Default constructor
    public ExcludedProductReferenceResponseDto() {
    }

    // All-args constructor
    public ExcludedProductReferenceResponseDto(String reference) {
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
