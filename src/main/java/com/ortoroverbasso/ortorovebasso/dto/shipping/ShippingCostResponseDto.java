package com.ortoroverbasso.ortorovebasso.dto.shipping;

public class ShippingCostResponseDto {

    private Double shippingCost;
    private CarrierResponseDto carrier;

    // Default constructor
    public ShippingCostResponseDto() {
    }

    // All-args constructor
    public ShippingCostResponseDto(Double shippingCost, CarrierResponseDto carrier) {
        this.shippingCost = shippingCost;
        this.carrier = carrier;
    }

    // Getters and Setters
    public Double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public CarrierResponseDto getCarrier() {
        return carrier;
    }

    public void setCarrier(CarrierResponseDto carrier) {
        this.carrier = carrier;
    }
}
