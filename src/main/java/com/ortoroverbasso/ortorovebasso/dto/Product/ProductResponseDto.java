package com.ortoroverbasso.ortorovebasso.dto.product;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceResponseDto;

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
    private Integer active;

    @Schema(description = "Se true allora ha un tag associato", example = "true")
    private Boolean tags;

    @Schema(description = "Se true allora ha un attribute associato", example = "true")
    private Boolean attributes;

    // Aggiungi i campi per i prezzi
    private String wholesalePrice;
    private Double inShopsPrice;

    private String reference;
    private Integer quantity;

    private Long manufacturer;

    private List<ProductLargeQuantityPriceResponseDto> priceLargeQuantities;

    // Costruttore vuoto

    public ProductResponseDto() {
    }

    public ProductResponseDto(
            String reference,
            Integer quantity) {
        this.reference = reference;
        this.quantity = quantity;

    }

    public ProductResponseDto(
            Long id,
            String sku,
            String retailPrice,
            Long category,
            Integer weight,
            Integer active,
            String wholesalePrice,
            Double inShopsPrice,
            Boolean tags,
            Long manufacturer,
            List<ProductLargeQuantityPriceResponseDto> priceLargeQuantities,
            Boolean attributes) {
        this.id = id;
        this.sku = sku;
        this.retailPrice = retailPrice;
        this.category = category;
        this.weight = weight;
        this.active = active;
        this.wholesalePrice = wholesalePrice;
        this.inShopsPrice = inShopsPrice;
        this.tags = tags;
        this.manufacturer = manufacturer;
        this.priceLargeQuantities = priceLargeQuantities;
        this.attributes = attributes;
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

    public Boolean getTags() {
        return tags;
    }

    public void setTags(Boolean tags) {
        this.tags = tags;
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

    public List<ProductLargeQuantityPriceResponseDto> getPriceLargeQuantities() {
        return priceLargeQuantities;
    }

    public void setPriceLargeQuantities(List<ProductLargeQuantityPriceResponseDto> priceLargeQuantities) {
        this.priceLargeQuantities = priceLargeQuantities;
    }

    public Long getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Long manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Boolean getAttributes() {
        return attributes;
    }

    public void setAttributes(Boolean attributes) {
        this.attributes = attributes;
    }
}
