package com.ortoroverbasso.ortorovebasso.dto.shipping;

public class ShippingCostByCountryResponseDto {
    private String reference;
    private String cost;
    private CarrierResponseDto carrier; // Utilizza CarrierResponseDto qui

    // Costruttore
    public ShippingCostByCountryResponseDto(String reference, String cost, CarrierResponseDto carrier) {
        this.reference = reference;
        this.cost = cost;
        this.carrier = carrier;
    }

    // Getters and Setters
    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public CarrierResponseDto getCarrier() {
        return carrier;
    }

    public void setCarrier(CarrierResponseDto carrier) {
        this.carrier = carrier;
    }
}
