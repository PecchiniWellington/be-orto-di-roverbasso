package com.ortoroverbasso.ortorovebasso.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;

public class ProductResponseDto {

    @Schema(description = "ID del prodotto", example = "1")
    private Long id;

    @Schema(description = "SKU del prodotto", example = "ABC123")
    private String sku;

    @Schema(description = "Prezzo al dettaglio del prodotto", example = "299.99")
    private String retailPrice;

    @Schema(description = "ID della categoria del prodotto", example = "1")
    private Long category;

    @Schema(description = "Peso del prodotto in grammi", example = "200")
    private Integer weight;

    @Schema(description = "Indica se il prodotto è attivo o meno", example = "true")
    private Boolean active;

    // Costruttore vuoto
    public ProductResponseDto() {
    }

    // Aggiungi i campi per i prezzi
    private Double wholesalePrice;
    private Double retailPriceFromPricing;
    private Double inShopsPrice;

    public ProductResponseDto(Long id, String sku, String retailPrice, Long category, Integer weight, boolean active) {
        this.id = id;
        this.sku = sku;
        this.retailPrice = retailPrice;
        this.category = category;
        this.weight = weight;
        this.active = active;
    }

    public ProductResponseDto(
            Long id,
            String sku, String retailPrice, Long category, Integer weight, boolean active,
            Double wholesalePrice, Double retailPriceFromPricing, Double inShopsPrice) {
        this.id = id;
        this.sku = sku;
        this.retailPrice = retailPrice;
        this.category = category;
        this.weight = weight;
        this.active = active;
        this.wholesalePrice = wholesalePrice;
        this.retailPriceFromPricing = retailPriceFromPricing;
        this.inShopsPrice = inShopsPrice;
    }

    // Getter e Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}
