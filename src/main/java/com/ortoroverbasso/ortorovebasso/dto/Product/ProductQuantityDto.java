package com.ortoroverbasso.ortorovebasso.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "DTO per la quantità di un prodotto in un ordine")
public class ProductQuantityDto {

    @NotNull(message = "Product ID is required")
    @Schema(description = "ID del prodotto", example = "1", required = true)
    private Long productId;

    @NotNull(message = "Quantity is required")
    @PositiveOrZero(message = "Quantity must be positive or zero")
    @Schema(description = "Quantità del prodotto", example = "2", required = true)
    private Integer quantity;

    @Schema(description = "Codice di riferimento del prodotto", example = "REF-ABC123")
    private String reference;

    // Constructors
    public ProductQuantityDto() {
    }

    public ProductQuantityDto(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public ProductQuantityDto(Long productId, Integer quantity, String reference) {
        this.productId = productId;
        this.quantity = quantity;
        this.reference = reference;
    }

    // Getters and Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return "ProductQuantityDto{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                ", reference='" + reference + '\'' +
                '}';
    }
}
