package com.ortoroverbasso.ortorovebasso.entity.tracking;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tracking_orders")
public class TrackingOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;

    @OneToMany
    private List<TrackingDetailEntity> trackings;

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

    public List<TrackingDetailEntity> getTrackings() {
        return trackings;
    }

    public void setTrackings(List<TrackingDetailEntity> trackings) {
        this.trackings = trackings;
    }
}
