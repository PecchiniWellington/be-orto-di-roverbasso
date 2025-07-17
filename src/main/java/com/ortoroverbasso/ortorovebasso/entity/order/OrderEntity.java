package com.ortoroverbasso.ortorovebasso.entity.order;

import java.time.LocalDateTime;
import java.util.List;

import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.shipping.CarriersEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.shipping_address.ShippingAddressEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String internalReference;

    private LocalDateTime dateAdd;

    private Double totalPaidTaxIncl;
    private Double totalPaidTaxExcl;
    private Double totalShippingTaxExcl;
    private Double totalShippingTaxIncl;

    private String status;

    @ManyToOne(cascade = CascadeType.ALL)
    private ShippingAddressEntity shippingAddress;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<ProductEntity> products;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<CarriersEntity> carriers;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getInternalReference() {
        return internalReference;
    }

    public void setInternalReference(String internalReference) {
        this.internalReference = internalReference;
    }

    public LocalDateTime getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(LocalDateTime dateAdd) {
        this.dateAdd = dateAdd;
    }

    public Double getTotalPaidTaxIncl() {
        return totalPaidTaxIncl;
    }

    public void setTotalPaidTaxIncl(Double totalPaidTaxIncl) {
        this.totalPaidTaxIncl = totalPaidTaxIncl;
    }

    public Double getTotalPaidTaxExcl() {
        return totalPaidTaxExcl;
    }

    public void setTotalPaidTaxExcl(Double totalPaidTaxExcl) {
        this.totalPaidTaxExcl = totalPaidTaxExcl;
    }

    public Double getTotalShippingTaxExcl() {
        return totalShippingTaxExcl;
    }

    public void setTotalShippingTaxExcl(Double totalShippingTaxExcl) {
        this.totalShippingTaxExcl = totalShippingTaxExcl;
    }

    public Double getTotalShippingTaxIncl() {
        return totalShippingTaxIncl;
    }

    public void setTotalShippingTaxIncl(Double totalShippingTaxIncl) {
        this.totalShippingTaxIncl = totalShippingTaxIncl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ShippingAddressEntity getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddressEntity shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public List<CarriersEntity> getCarriers() {
        return carriers;
    }

    public void setCarriers(List<CarriersEntity> carriers) {
        this.carriers = carriers;
    }
}
