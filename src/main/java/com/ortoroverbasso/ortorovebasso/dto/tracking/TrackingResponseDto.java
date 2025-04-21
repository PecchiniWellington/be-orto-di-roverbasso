package com.ortoroverbasso.ortorovebasso.dto.tracking;

import java.util.List;

public class TrackingResponseDto {

    private Long id;
    private String reference;
    private List<TrackingDetailDto> trackings;

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

    public List<TrackingDetailDto> getTrackings() {
        return trackings;
    }

    public void setTrackings(List<TrackingDetailDto> trackings) {
        this.trackings = trackings;
    }

    // DTO per i dettagli del tracciamento
    public static class TrackingDetailDto {

        private String trackingNumber;
        private String statusDescription;
        private String statusDate;
        private CarrierDto carrier;
        private String descriptionTranslated;

        // Getters and Setters
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

        public CarrierDto getCarrier() {
            return carrier;
        }

        public void setCarrier(CarrierDto carrier) {
            this.carrier = carrier;
        }

        public String getDescriptionTranslated() {
            return descriptionTranslated;
        }

        public void setDescriptionTranslated(String descriptionTranslated) {
            this.descriptionTranslated = descriptionTranslated;
        }

        // DTO per il Carrier
        public static class CarrierDto {

            private Long id;
            private String name;

            // Getters and Setters
            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
