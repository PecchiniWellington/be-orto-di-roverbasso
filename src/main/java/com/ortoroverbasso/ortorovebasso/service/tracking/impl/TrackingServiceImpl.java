package com.ortoroverbasso.ortorovebasso.service.tracking.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
        return restTemplate.postForObject(TRACKING_API_URL, trackingRequest, TrackingResponseDto.class);
    }

    public TrackingResponseDto getTrackingOrder(TrackingRequestDto requestDto) {
        String url = TRACKING_API_URL.replace("{format}", "json");

        ResponseEntity<TrackingResponseDto[]> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new org.springframework.http.HttpEntity<>(requestDto),
                TrackingResponseDto[].class);

        return response.getBody()[0];
    }
}
