package com.ortoroverbasso.ortorovebasso.entity.order_custom;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.entity.cart.CartEntity;
import com.ortoroverbasso.ortorovebasso.entity.pickup.PickupEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @Column(name = "status_order")
    private String statusOrder;

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

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public CartEntity getCart() {
        return cart;
    }

    public void setCart(CartEntity cart) {
        this.cart = cart;
    }

}
