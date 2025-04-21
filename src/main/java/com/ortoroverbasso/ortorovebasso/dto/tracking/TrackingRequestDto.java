package com.ortoroverbasso.ortorovebasso.dto.tracking;

import java.util.List;

public class TrackingRequestDto {

    private TrackingRequest track;

    // Getters and Setters
    public TrackingRequest getTrack() {
        return track;
    }

    public void setTrack(TrackingRequest track) {
        this.track = track;
    }

    public static class TrackingRequest {
        private List<OrderId> orders;

        // Getters and Setters
        public List<OrderId> getOrders() {
            return orders;
        }

        public void setOrders(List<OrderId> orders) {
            this.orders = orders;
        }
    }

    public static class OrderId {
        private String id;

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
