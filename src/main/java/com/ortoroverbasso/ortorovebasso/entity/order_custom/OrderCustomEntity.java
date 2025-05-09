package com.ortoroverbasso.ortorovebasso.entity.order_custom;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.entity.pickup.PickupEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_custom")
public class OrderCustomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "order_custom_products", joinColumns = @JoinColumn(name = "order_custom_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<ProductEntity> products;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pickup_id")
    private PickupEntity pickupOrder;

    @Column(name = "status_order")
    private String statusOrder;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public PickupEntity getPickupOrder() {
        return pickupOrder;
    }

    public void setPickupOrder(PickupEntity pickupOrder) {
        this.pickupOrder = pickupOrder;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }
}
