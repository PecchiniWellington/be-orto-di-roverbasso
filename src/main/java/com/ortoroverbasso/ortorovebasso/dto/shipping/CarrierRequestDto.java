package com.ortoroverbasso.ortorovebasso.dto.shipping;

public class CarrierRequestDto {

    private Long id;
    private String name;
    private String price;

    public CarrierRequestDto() {

    }

    public CarrierRequestDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Costruttore
    public CarrierRequestDto(Long id, String name, String price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Getters and Setters
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
