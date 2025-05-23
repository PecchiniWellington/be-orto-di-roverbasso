package com.ortoroverbasso.ortorovebasso.entity.order_custom;

import java.time.LocalDateTime;
import java.util.List;

import com.ortoroverbasso.ortorovebasso.entity.cart.CartEntity;
import com.ortoroverbasso.ortorovebasso.entity.pickup.PickupEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.enums.StatusOrderEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_custom")
public class OrderCustomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderCustomProductEntity> orderProducts;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pickup_id")
    private PickupEntity pickupOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private CartEntity cart;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_order")
    StatusOrderEnum statusOrder;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

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

    public List<OrderCustomProductEntity> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderCustomProductEntity> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public PickupEntity getPickupOrder() {
        return pickupOrder;
    }

    public void setPickupOrder(PickupEntity pickupOrder) {
        this.pickupOrder = pickupOrder;
    }

    public StatusOrderEnum getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(StatusOrderEnum statusOrder) {
        this.statusOrder = statusOrder;
    }

    public CartEntity getCart() {
        return cart;
    }

    public void setCart(CartEntity cart) {
        this.cart = cart;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
