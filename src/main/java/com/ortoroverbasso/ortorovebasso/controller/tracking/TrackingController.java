package com.ortoroverbasso.ortorovebasso.controller.tracking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.tracking.TrackingRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.tracking.TrackingResponseDto;
import com.ortoroverbasso.ortorovebasso.service.tracking.ITrackingService;

@RestController
@RequestMapping("/api/tracking")
public class TrackingController {

    @Autowired
    private ITrackingService trackingService;

    @PostMapping("/orders")
    public ResponseEntity<TrackingResponseDto> trackOrders(@RequestBody TrackingRequestDto trackingRequest) {
        TrackingResponseDto trackingResponse = trackingService.trackOrders(trackingRequest);
        return ResponseEntity.ok(trackingResponse);
    }
}
