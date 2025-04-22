package com.ortoroverbasso.ortorovebasso.dto.product;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceRequestDto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ProductRequestDto {

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
    private Integer active;

    // Aggiungi i campi per i prezzi
    private String wholesalePrice;
    private Double inShopsPrice;

    private String reference;
    private Integer quantity;

    private List<ProductLargeQuantityPriceRequestDto> priceLargeQuantities;

    // Costruttore vuoto

    public ProductRequestDto() {
    }

    public ProductRequestDto(
            String reference,
            Integer quantity) {
        this.reference = reference;
        this.quantity = quantity;

    }

    public ProductRequestDto(
            Long id,
            String sku,
            String retailPrice,
            Long category,
            Integer weight,
            Integer active,
            String wholesalePrice,
            Double inShopsPrice,
            List<ProductLargeQuantityPriceRequestDto> priceLargeQuantities) {
        this.id = id;
        this.sku = sku;
        this.retailPrice = retailPrice;
        this.category = category;
        this.weight = weight;
        this.active = active;
        this.wholesalePrice = wholesalePrice;
        this.inShopsPrice = inShopsPrice;
        this.priceLargeQuantities = priceLargeQuantities;
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

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(String wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public Double getInShopsPrice() {
        return inShopsPrice;
    }

    public void setInShopsPrice(Double inShopsPrice) {
        this.inShopsPrice = inShopsPrice;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<ProductLargeQuantityPriceRequestDto> getPriceLargeQuantities() {
        return priceLargeQuantities;
    }

    public void setPriceLargeQuantities(List<ProductLargeQuantityPriceRequestDto> priceLargeQuantities) {
        this.priceLargeQuantities = priceLargeQuantities;
    }
}
