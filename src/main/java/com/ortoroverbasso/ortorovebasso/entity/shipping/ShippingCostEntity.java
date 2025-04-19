package com.ortoroverbasso.ortorovebasso.entity.shipping;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "shipping_costs")
public class ShippingCostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;
    private Double cost;

    // Relazione Many-to-One con CarriersEntity
    @ManyToOne
    @JoinColumn(name = "carrier_id", referencedColumnName = "id")
    private CarriersEntity carrier;

    // Default constructor
    public ShippingCostEntity() {
    }

    // Constructor with parameters
    public ShippingCostEntity(String reference, Double cost, CarriersEntity carrier) {
        this.reference = reference;
        this.cost = cost;
        this.carrier = carrier;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public CarriersEntity getCarrier() {
        return carrier;
    }

    public void setCarrier(CarriersEntity carrier) {
        this.carrier = carrier;
    }
}
