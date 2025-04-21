package com.ortoroverbasso.ortorovebasso.service.tracking.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ortoroverbasso.ortorovebasso.dto.tracking.TrackingRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.tracking.TrackingResponseDto;
import com.ortoroverbasso.ortorovebasso.service.tracking.ITrackingService;

@Service
public class TrackingServiceImpl implements ITrackingService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String TRACKING_API_URL = "https://api.bigbuy.eu/rest/tracking/orders";

    @Override
    public TrackingResponseDto trackOrders(TrackingRequestDto trackingRequest) {
        // Invio la richiesta POST e ricevo la risposta
        return restTemplate.postForObject(TRACKING_API_URL, trackingRequest, TrackingResponseDto.class);
    }
}
