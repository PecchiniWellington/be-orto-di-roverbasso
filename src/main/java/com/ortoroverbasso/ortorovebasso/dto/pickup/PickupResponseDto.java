package com.ortoroverbasso.ortorovebasso.dto.pickup;

import java.time.LocalDate;
import java.time.LocalTime;

public class PickupResponseDto {

    private Long id;
    private LocalDate pickupDate;
    private LocalTime pickupTime;
    private String fullName;
    private String phone;
    private String transportType;
    private String loadAssistance;
    private String token;
    private Long orderId; // Add this field

    // Costruttore vuoto
    public PickupResponseDto() {
    }

    // Costruttore completo
    public PickupResponseDto(
            Long id,
            LocalDate pickupDate,
            LocalTime pickupTime,
            String fullName,
            String phone,
            String transportType,
            String loadAssistance,
            String token) {
        this.id = id;
        this.pickupDate = pickupDate;
        this.pickupTime = pickupTime;
        this.fullName = fullName;
        this.phone = phone;
        this.transportType = transportType;
        this.loadAssistance = loadAssistance;
        this.token = token;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

}
