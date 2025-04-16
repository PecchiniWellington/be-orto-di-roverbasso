package com.ortoroverbasso.ortorovebasso.entity.product.product_large_quantities_price;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "price_large_quantity")
@Entity
public class ProductLargeQuantityPriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private Double price;

    // Default constructor
    public ProductLargeQuantityPriceEntity() {
    }

    // All-args constructor
    public ProductLargeQuantityPriceEntity(Long id, Integer quantity, Double price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ProductLargeQuantityPriceEntity that = (ProductLargeQuantityPriceEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null)
            return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null)
            return false;
        if (price != null ? !price.equals(that.price) : that.price != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);

        return result;
    }

    // ToString
    @Override
    public String toString() {
        return "PriceLargeQuantityEntity{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
