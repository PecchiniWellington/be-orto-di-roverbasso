package com.ortoroverbasso.ortorovebasso.dto.shipping;

public class CarrierResponseDto {

    private String id;
    private String name;

    // Costruttore
    public CarrierResponseDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
