package com.ortoroverbasso.ortorovebasso.dto.product;

import java.math.BigDecimal;
import java.util.List;

import com.ortoroverbasso.ortorovebasso.constants.product.ProductConstants;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceRequestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO per la richiesta di creazione/aggiornamento prodotto")
public class ProductRequestDto {

    @Schema(description = "ID del prodotto (solo per aggiornamento)", example = "1")
    private Long id;

    @NotBlank(message = ProductConstants.SKU_REQUIRED)
    @Size(max = ProductConstants.SKU_MAX_LENGTH, message = "SKU must not exceed " + ProductConstants.SKU_MAX_LENGTH
            + " characters")
    @Schema(description = "SKU del prodotto", example = "ABC123", required = true)
    private String sku;

    @NotNull(message = ProductConstants.RETAIL_PRICE_REQUIRED)
    @DecimalMin(value = "0.0", message = "Il prezzo al dettaglio deve essere positivo")
    @Schema(description = "Prezzo al dettaglio del prodotto", example = "299.99", required = true)
    private BigDecimal retailPrice;

    @NotNull(message = ProductConstants.CATEGORY_REQUIRED)
    @Schema(description = "ID della categoria del prodotto", example = "1", required = true)
    private Long categoryId;

    @PositiveOrZero(message = "Il peso deve essere positivo o zero")
    @Schema(description = "Peso del prodotto in grammi", example = "200")
    private BigDecimal weight;

    @NotNull(message = ProductConstants.ACTIVE_STATUS_REQUIRED)
    @Schema(description = "Indica se il prodotto è attivo o meno", example = "true", required = true)
    private Boolean active = ProductConstants.DEFAULT_ACTIVE;

    @DecimalMin(value = "0.0", message = "Il prezzo all'ingrosso deve essere positivo")
    @Schema(description = "Prezzo all'ingrosso del prodotto", example = "199.99")
    private BigDecimal wholesalePrice;

    @DecimalMin(value = "0.0", message = "Il prezzo nei negozi deve essere positivo")
    @Schema(description = "Prezzo del prodotto nei negozi", example = "249.99")
    private BigDecimal inShopsPrice;

    @Size(max = ProductConstants.REFERENCE_MAX_LENGTH, message = "Reference must not exceed "
            + ProductConstants.REFERENCE_MAX_LENGTH + " characters")
    @Schema(description = "Codice di riferimento del prodotto", example = "REF-ABC123")
    private String reference;

    @PositiveOrZero(message = "La quantità deve essere positiva o zero")
    @Schema(description = "Quantità disponibile", example = "100")
    private Integer quantity = ProductConstants.DEFAULT_QUANTITY;

    @PositiveOrZero(message = "Lo sconto deve essere positivo o zero")
    @Schema(description = "Percentuale di sconto", example = "10")
    private Integer discount = ProductConstants.DEFAULT_DISCOUNT;

    @Schema(description = "ID del produttore", example = "5")
    private Long manufacturerId;

    @Valid
    @Schema(description = "Lista dei prezzi per grandi quantità")
    private List<ProductLargeQuantityPriceRequestDto> priceLargeQuantities;

    // Constructors
    public ProductRequestDto() {
    }

    public ProductRequestDto(String sku, BigDecimal retailPrice, Long categoryId, Boolean active) {
        this.sku = sku;
        this.retailPrice = retailPrice;
        this.categoryId = categoryId;
        this.active = active;
    }

    // Getters and Setters
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

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public BigDecimal getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(BigDecimal wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public BigDecimal getInShopsPrice() {
        return inShopsPrice;
    }

    public void setInShopsPrice(BigDecimal inShopsPrice) {
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

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public List<ProductLargeQuantityPriceRequestDto> getPriceLargeQuantities() {
        return priceLargeQuantities;
    }

    public void setPriceLargeQuantities(List<ProductLargeQuantityPriceRequestDto> priceLargeQuantities) {
        this.priceLargeQuantities = priceLargeQuantities;
    }
}
