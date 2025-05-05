package com.ortoroverbasso.ortorovebasso.dto.category;

public class SubCategoryResponseDto {

    private Long id;
    private String name;

    // Costruttore con parametri
    public SubCategoryResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter e Setter
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
}
