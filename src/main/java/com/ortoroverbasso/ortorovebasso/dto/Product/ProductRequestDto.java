package com.ortoroverbasso.ortorovebasso.dto.product;

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

    // Additional fields for ProductInformation
    @Schema(description = "Nome del prodotto", example = "Prodotto XYZ")
    private String name;

    @Schema(description = "Descrizione del prodotto", example = "Descrizione del prodotto XYZ")
    private String description;

    @Schema(description = "URL del prodotto", example = "http://example.com/prodotto-xyz")
    private String url;

    @Schema(description = "Codice ISO del prodotto", example = "IT")
    private String isoCode;

    @Schema(description = "Data di aggiornamento della descrizione", example = "2025-04-14T14:00:00")
    private String dateUpdDescription;

    private String reference;
    private Integer quantity;

    public ProductRequestDto() {
    }

    public ProductRequestDto(
            String reference,
            Integer quantity) {
        this.reference = reference;
        this.quantity = quantity;

    }

    public ProductRequestDto(String sku, String ean13, Integer weight, Long category, String retailPrice,
            Boolean active, String name, String description, String url, String isoCode, String dateUpdDescription) {
        this.sku = sku;
        this.ean13 = ean13;
        this.weight = weight;
        this.category = category;
        this.retailPrice = retailPrice;
        this.active = active;
        this.name = name;
        this.description = description;
        this.url = url;
        this.isoCode = isoCode;
        this.dateUpdDescription = dateUpdDescription;
    }

    // Getters and Setters for all fields

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

    // Additional getters and setters for ProductInformation
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getDateUpdDescription() {
        return dateUpdDescription;
    }

    public void setDateUpdDescription(String dateUpdDescription) {
        this.dateUpdDescription = dateUpdDescription;
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

}
