package com.ortoroverbasso.ortorovebasso.dto.pickup;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PickupRequestDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate pickupDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime pickupTime;
    private String fullName;
    private String phone;
    private String transportType;
    private String loadAssistance;
    private String token;
    private String cartToken;

    // Costruttore vuoto
    public PickupRequestDto() {
    }

    // Costruttore completo
    public PickupRequestDto(
            LocalDate pickupDate,
            LocalTime pickupTime,
            String fullName,
            String phone,
            String transportType,
            String loadAssistance,
            String token) {
        this.pickupDate = pickupDate;
        this.pickupTime = pickupTime;
        this.fullName = fullName;
        this.phone = phone;
        this.transportType = transportType;
        this.loadAssistance = loadAssistance;
        this.token = token;
    }

    // Getters e Setters
    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public LocalTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getLoadAssistance() {
        return loadAssistance;
    }

    public void setLoadAssistance(String loadAssistance) {
        this.loadAssistance = loadAssistance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCartToken() {
        return cartToken;
    }

    public void setCartToken(String cartToken) {
        this.cartToken = cartToken;
    }

}
