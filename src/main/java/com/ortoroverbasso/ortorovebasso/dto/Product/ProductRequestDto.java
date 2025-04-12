package com.ortoroverbasso.ortorovebasso.dto.Product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class ProductRequestDto {

    @Schema(description = "SKU del prodotto", example = "ABC123")
    private String sku;

    @Schema(description = "EAN13 del prodotto", example = "1234567890123")
    private String ean13;

    @NotNull(message = "Il peso del prodotto è obbligatorio")
    @Schema(description = "Peso del prodotto in grammi", example = "200")
    private Integer weight;

    @NotNull(message = "La categoria del prodotto è obbligatoria")
    @Schema(description = "ID della categoria del prodotto", example = "1")
    private Long category;

    @Schema(description = "Prezzo al dettaglio del prodotto", example = "299.99")
    private String retailPrice;

    @NotNull(message = "Il prodotto deve essere attivo o meno")
    @Schema(description = "Indica se il prodotto è attivo o meno", example = "true")
    private Boolean active;

    // Costruttore, Getters e Setters
    public ProductRequestDto(String sku, String ean13, Integer weight, Long category, String retailPrice,
            Boolean active) {
        this.sku = sku;
        this.ean13 = ean13;
        this.weight = weight;
        this.category = category;
        this.retailPrice = retailPrice;
        this.active = active;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getEan13() {
        return ean13;
    }

    public void setEan13(String ean13) {
        this.ean13 = ean13;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
