package com.ortoroverbasso.ortorovebasso.entity.product.product_large_quantities_price;

import java.math.BigDecimal;

import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_variation.ProductVariationEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "product_large_quantity_prices")
public class ProductLargeQuantityPriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be positive")
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variation_id")
    private ProductVariationEntity productVariation;

    // Constructors
    public ProductLargeQuantityPriceEntity() {
    }

    public ProductLargeQuantityPriceEntity(Integer quantity, BigDecimal price) {
        this.quantity = quantity;
        this.price = price;
    }

    public ProductLargeQuantityPriceEntity(Integer quantity, BigDecimal price, Boolean isActive) {
        this.quantity = quantity;
        this.price = price;
        this.isActive = isActive;
    }

    // Utility methods
    public boolean isActive() {
        return Boolean.TRUE.equals(isActive);
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }

    /**
     * Calcola il prezzo totale per la quantità specificata
     */
    public BigDecimal calculateTotalPrice() {
        if (quantity != null && price != null) {
            return price.multiply(BigDecimal.valueOf(quantity));
        }
        return BigDecimal.ZERO;
    }

    /**
     * Calcola il risparmio per unità rispetto al prezzo al dettaglio
     */
    public BigDecimal calculateSavingsPerUnit(BigDecimal retailPrice) {
        if (retailPrice != null && price != null && retailPrice.compareTo(price) > 0) {
            return retailPrice.subtract(price);
        }
        return BigDecimal.ZERO;
    }

    /**
     * Calcola la percentuale di sconto rispetto al prezzo al dettaglio
     */
    public BigDecimal calculateDiscountPercentage(BigDecimal retailPrice) {
        if (retailPrice != null && price != null && retailPrice.compareTo(BigDecimal.ZERO) > 0) {
            return retailPrice.subtract(price)
                    .divide(retailPrice, 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }
        return BigDecimal.ZERO;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public ProductVariationEntity getProductVariation() {
        return productVariation;
    }

    public void setProductVariation(ProductVariationEntity productVariation) {
        this.productVariation = productVariation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ProductLargeQuantityPriceEntity))
            return false;

        ProductLargeQuantityPriceEntity that = (ProductLargeQuantityPriceEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null)
            return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null)
            return false;
        return price != null ? price.equals(that.price) : that.price == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductLargeQuantityPriceEntity{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", price=" + price +
                ", isActive=" + isActive +
                '}';
    }
}
