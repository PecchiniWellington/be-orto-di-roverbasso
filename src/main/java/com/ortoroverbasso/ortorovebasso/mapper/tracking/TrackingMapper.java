package com.ortoroverbasso.ortorovebasso.mapper.tracking;

import java.util.List;
import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.tracking.TrackingResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.shipping.CarriersEntity;
import com.ortoroverbasso.ortorovebasso.entity.tracking.TrackingDetailEntity;
import com.ortoroverbasso.ortorovebasso.entity.tracking.TrackingOrderEntity;

public class TrackingMapper {

    public static TrackingResponseDto toResponse(TrackingOrderEntity entity) {
        TrackingResponseDto dto = new TrackingResponseDto();
        dto.setId(entity.getId());
        dto.setReference(entity.getReference());

        // Mappare la lista di tracking details
        List<TrackingResponseDto.TrackingDetailDto> trackings = entity.getTrackings().stream()
                .map(trackingEntity -> {
                    TrackingResponseDto.TrackingDetailDto trackingDto = new TrackingResponseDto.TrackingDetailDto();
                    trackingDto.setTrackingNumber(trackingEntity.getTrackingNumber());
                    trackingDto.setStatusDescription(trackingEntity.getStatusDescription());
                    trackingDto.setStatusDate(trackingEntity.getStatusDate());
                    trackingDto.setDescriptionTranslated(trackingEntity.getDescriptionTranslated());

                    // Mappare il carrier
                    TrackingResponseDto.TrackingDetailDto.CarrierDto carrierDto = new TrackingResponseDto.TrackingDetailDto.CarrierDto();
                    carrierDto.setId(trackingEntity.getCarrier().getId());
                    carrierDto.setName(trackingEntity.getCarrier().getName());
                    trackingDto.setCarrier(carrierDto);

                    return trackingDto;
                })
                .collect(Collectors.toList());

        dto.setTrackings(trackings);
        return dto;
    }

    // Metodo per convertire i DTO in Entity (se necessario per il caso di utilizzo
    // del POST)
    public static TrackingOrderEntity toEntity(TrackingResponseDto dto) {
        TrackingOrderEntity entity = new TrackingOrderEntity();
        entity.setReference(dto.getReference());

        // Mappare la lista di tracking details
        List<TrackingDetailEntity> trackings = dto.getTrackings().stream()
                .map(trackingDto -> {
                    TrackingDetailEntity trackingEntity = new TrackingDetailEntity();
                    trackingEntity.setTrackingNumber(trackingDto.getTrackingNumber());
                    trackingEntity.setStatusDescription(trackingDto.getStatusDescription());
                    trackingEntity.setStatusDate(trackingDto.getStatusDate());
                    trackingEntity.setDescriptionTranslated(trackingDto.getDescriptionTranslated());

                    // Mappare il carrier
                    CarriersEntity carrierEntity = new CarriersEntity();
                    carrierEntity.setId(trackingDto.getCarrier().getId());
                    carrierEntity.setName(trackingDto.getCarrier().getName());
                    trackingEntity.setCarrier(carrierEntity);

                    return trackingEntity;
                })
                .collect(Collectors.toList());

        entity.setTrackings(trackings);
        return entity;
    }
}
