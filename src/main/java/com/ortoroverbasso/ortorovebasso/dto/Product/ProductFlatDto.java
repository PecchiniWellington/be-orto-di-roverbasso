package com.ortoroverbasso.ortorovebasso.dto.product;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO semplificato per rappresentazione flat dei prodotti")
public class ProductFlatDto {

    @Schema(description = "ID del prodotto", example = "1")
    private Long id;

    @Schema(description = "SKU del prodotto", example = "ABC123")
    private String sku;

    @Schema(description = "Codice di riferimento", example = "REF-ABC123")
    private String reference;

    @Schema(description = "URL dell'immagine principale", example = "https://example.com/image.jpg")
    private String imageUrl;

    @Schema(description = "Nome del prodotto", example = "Prodotto di esempio")
    private String name;

    @Schema(description = "Prezzo al dettaglio", example = "299.99")
    private BigDecimal retailPrice;

    // Costruttore vuoto richiesto da Spring
    public ProductFlatDto() {
    }

    // Costruttore usato nella JPQL
    public ProductFlatDto(Long id, String sku, String reference, String imageUrl, String name, BigDecimal retailPrice) {
        this.id = id;
        this.sku = sku;
        this.reference = reference;
        this.imageUrl = imageUrl;
        this.name = name;
        this.retailPrice = retailPrice;
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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    @Override
    public String toString() {
        return "ProductFlatDto{" +
                "id=" + id +
                ", sku='" + sku + '\'' +
                ", reference='" + reference + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", name='" + name + '\'' +
                ", retailPrice=" + retailPrice +
                '}';
    }
}
