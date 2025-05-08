package com.ortoroverbasso.ortorovebasso.entity.pickup;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pickups")
public class PickupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate pickupDate;
    private LocalTime pickupTime;
    private String fullName;
    private String phone;
    private String transportType;
    private String loadAssistance;
    private String token; // Nuovo campo token

    // Costruttore vuoto
    public PickupEntity() {
    }

    // Costruttore completo aggiornato con token
    public PickupEntity(LocalDate pickupDate, LocalTime pickupTime, String fullName,
            String phone, String transportType, String loadAssistance, String token) {
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
}
