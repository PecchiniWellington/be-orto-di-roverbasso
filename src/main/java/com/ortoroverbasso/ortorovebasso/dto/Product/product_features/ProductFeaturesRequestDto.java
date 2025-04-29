package com.ortoroverbasso.ortorovebasso.dto.product.product_features;

public class ProductFeaturesRequestDto {
    private Long id;
    private String feature;
    private String value;

    // Default constructor
    public ProductFeaturesRequestDto() {
    }

    // All-args constructor
    public ProductFeaturesRequestDto(
            Long id,
            String feature,
            String value) {
        this.id = id;
        this.feature = feature;
        this.value = value;
    }

    // Getter and Setter for feature
    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    // Getter and Setter for value
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    // Getter and Setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
