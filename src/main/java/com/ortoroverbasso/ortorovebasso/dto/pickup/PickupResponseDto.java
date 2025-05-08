package com.ortoroverbasso.ortorovebasso.dto.pickup;

public class PickupResponseDto {

    private Long id;
    private String pickupDate;
    private String pickupTime;
    private String fullName;
    private String phone;
    private String transportType;
    private String loadAssistance;

    // Costruttore vuoto
    public PickupResponseDto() {
    }

    // Costruttore completo
    public PickupResponseDto(Long id, String pickupDate, String pickupTime, String fullName,
            String phone, String transportType, String loadAssistance) {
        this.id = id;
        this.pickupDate = pickupDate;
        this.pickupTime = pickupTime;
        this.fullName = fullName;
        this.phone = phone;
        this.transportType = transportType;
        this.loadAssistance = loadAssistance;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
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
}
