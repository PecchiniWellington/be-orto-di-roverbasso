package com.ortoroverbasso.ortorovebasso.dto.orders;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "DTO per rappresentare un prodotto in un ordine")
public class OrderProductDto {

    @NotNull(message = "Product ID is required")
    @Schema(description = "ID del prodotto", example = "1", required = true)
    private Long productId;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    @Schema(description = "Quantità ordinata", example = "2", required = true)
    private Integer quantity;

    @Schema(description = "Codice di riferimento del prodotto", example = "REF-ABC123")
    private String reference;

    @Schema(description = "Prezzo unitario al momento dell'ordine", example = "99.99")
    private Double unitPrice;

    @Schema(description = "Prezzo totale per questa riga dell'ordine", example = "199.98")
    private Double totalPrice;

    @Schema(description = "SKU del prodotto", example = "ABC123")
    private String sku;

    @Schema(description = "Nome del prodotto al momento dell'ordine", example = "Prodotto Example")
    private String productName;

    // Constructors
    public OrderProductDto() {}

    public OrderProductDto(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public OrderProductDto(Long productId, Integer quantity, String reference, Double unitPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.reference = reference;
        this.unitPrice = unitPrice;
        this.totalPrice = unitPrice != null ? unitPrice * quantity : null;
    }

    // Utility methods
    public Double calculateTotalPrice() {
        if (unitPrice != null && quantity != null) {
            return unitPrice * quantity;
        }
        return null;
    }

    public void updateTotalPrice() {
        this.totalPrice = calculateTotalPrice();
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
        updateTotalPrice(); // Aggiorna automaticamente il totale
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
        updateTotalPrice(); // Aggiorna automaticamente il totale
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderProductDto that = (OrderProductDto) o;

        if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
        return reference != null ? reference.equals(that.reference) : that.reference == null;
    }

    @Override
    public int hashCode() {
        int result = productId != null ? productId.hashCode() : 0;
        result = 31 * result + (reference != null ? reference.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderProductDto{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                ", reference='" + reference + '\'' +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + totalPrice +
                ", sku='" + sku + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}