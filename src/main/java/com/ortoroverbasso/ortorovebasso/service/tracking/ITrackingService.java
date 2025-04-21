package com.ortoroverbasso.ortorovebasso.service.tracking;

import com.ortoroverbasso.ortorovebasso.dto.tracking.TrackingRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.tracking.TrackingResponseDto;

public interface ITrackingService {
    TrackingResponseDto trackOrders(TrackingRequestDto trackingRequest);
}
