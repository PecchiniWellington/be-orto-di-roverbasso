package com.ortoroverbasso.ortorovebasso.dto.manufacturer;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class ManufacturerResponseDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String urlImage;
    private Integer reference;
    private List<ProductResponseDto> products;

    public ManufacturerResponseDto() {
    }

    public ManufacturerResponseDto(
            Long id,
            String name,
            String urlImage,
            Integer reference,
            List<ProductResponseDto> products) {
        this.id = id;
        this.name = name;
        this.urlImage = urlImage;
        this.reference = reference;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Integer getReference() {
        return reference;
    }

    public void setReference(Integer reference) {
        this.reference = reference;
    }

    public List<ProductResponseDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponseDto> products) {
        this.products = products;
    }
}
