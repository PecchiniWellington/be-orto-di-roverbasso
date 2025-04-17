package com.ortoroverbasso.ortorovebasso.dto.product.product_attributes;

public class ProductAttributesRequestDto {

    private long id;
    private int attributeGroup;
    private String name;
    private String isoCode;

    // Default constructor
    public ProductAttributesRequestDto() {
    }

    // All-args constructor
    public ProductAttributesRequestDto(long id, int attributeGroup, String name, String isoCode) {
        this.id = id;
        this.attributeGroup = attributeGroup;
        this.name = name;
        this.isoCode = isoCode;
    }

    // Getter and Setter for id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // Getter and Setter for attributeGroup
    public int getAttributeGroup() {
        return attributeGroup;
    }

    public void setAttributeGroup(int attributeGroup) {
        this.attributeGroup = attributeGroup;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for isoCode
    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }
}
