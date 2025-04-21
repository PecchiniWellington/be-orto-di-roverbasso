package com.ortoroverbasso.ortorovebasso.entity.tracking;

import com.ortoroverbasso.ortorovebasso.entity.shipping.CarriersEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tracking_details")
public class TrackingDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trackingNumber;
    private String statusDescription;
    private String statusDate;

    @ManyToOne
    private CarriersEntity carrier;

    private String descriptionTranslated;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    public CarriersEntity getCarrier() {
        return carrier;
    }

    public void setCarrier(CarriersEntity carrier) {
        this.carrier = carrier;
    }

    public String getDescriptionTranslated() {
        return descriptionTranslated;
    }

    public void setDescriptionTranslated(String descriptionTranslated) {
        this.descriptionTranslated = descriptionTranslated;
    }
}
