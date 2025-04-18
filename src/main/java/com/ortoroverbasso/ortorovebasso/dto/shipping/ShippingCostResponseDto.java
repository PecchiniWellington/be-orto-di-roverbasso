package com.ortoroverbasso.ortorovebasso.dto.shipping;

public class ShippingCostResponseDto {

    private double shippingCost;
    private CarrierResponseDto carrier;

    // Getters and Setters
    public double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public CarrierResponseDto getCarrier() {
        return carrier;
    }

    public void setCarrier(CarrierResponseDto carrier) {
        this.carrier = carrier;
    }
}
