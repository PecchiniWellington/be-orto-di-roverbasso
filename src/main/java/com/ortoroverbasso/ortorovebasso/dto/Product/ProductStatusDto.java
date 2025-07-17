package com.ortoroverbasso.ortorovebasso.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO per l'aggiornamento dello stato di un prodotto")
public class ProductStatusDto {

    @NotNull(message = "Product ID is required")
    @Schema(description = "ID del prodotto", example = "1", required = true)
    private Long productId;

    @NotNull(message = "Active status is required")
    @Schema(description = "Nuovo stato attivo del prodotto", example = "true", required = true)
    private Boolean active;

    // Constructors
    public ProductStatusDto() {
    }

    public ProductStatusDto(Long productId, Boolean active) {
        this.productId = productId;
        this.active = active;
    }

    // Getters and Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "ProductStatusDto{" +
                "productId=" + productId +
                ", active=" + active +
                '}';
    }
}
