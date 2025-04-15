package com.ortoroverbasso.ortorovebasso.entity.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "price_large_quantity")
@Entity
public class ProductPriceLargeQuantityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private Double price;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "pricing_info_id")
    private ProductPricingInfoEntity productPricingInfo;

    // Default constructor
    public ProductPriceLargeQuantityEntity() {
    }

    // All-args constructor
    public ProductPriceLargeQuantityEntity(Long id, Integer quantity, Double price, ProductEntity product) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.product = product;
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

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ProductPriceLargeQuantityEntity that = (ProductPriceLargeQuantityEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null)
            return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null)
            return false;
        if (price != null ? !price.equals(that.price) : that.price != null)
            return false;
        return product != null ? product.equals(that.product) : that.product == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (product != null ? product.hashCode() : 0);
        return result;
    }

    // ToString
    @Override
    public String toString() {
        return "PriceLargeQuantityEntity{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", price=" + price +
                ", product=" + product +
                '}';
    }
}
