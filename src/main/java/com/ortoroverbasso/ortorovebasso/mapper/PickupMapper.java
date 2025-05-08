package com.ortoroverbasso.ortorovebasso.mapper;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.ortoroverbasso.ortorovebasso.dto.pickup.PickupRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.pickup.PickupResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.pickup.PickupEntity;

@Component
public class PickupMapper {

    // Converti DTO in Entity
    public PickupEntity toEntity(PickupRequestDto dto) {
        if (dto == null) {
            return null;
        }

        PickupEntity entity = new PickupEntity();

        if (dto.getPickupDate() != null) {
            entity.setPickupDate(LocalDate.parse(dto.getPickupDate()));
        }

        if (dto.getPickupTime() != null) {
            entity.setPickupTime(LocalTime.parse(dto.getPickupTime()));
        }

        entity.setFullName(dto.getFullName());
        entity.setPhone(dto.getPhone());
        entity.setTransportType(dto.getTransportType());
        entity.setLoadAssistance(dto.getLoadAssistance());

        return entity;
    }

    // Converti Entity in DTO
    public PickupResponseDto toDto(PickupEntity entity) {
        if (entity == null) {
            return null;
        }

        PickupResponseDto dto = new PickupResponseDto();

        dto.setId(entity.getId());

        if (entity.getPickupDate() != null) {
            dto.setPickupDate(entity.getPickupDate().toString());
        }

        if (entity.getPickupTime() != null) {
            dto.setPickupTime(entity.getPickupTime().toString());
        }

        dto.setFullName(entity.getFullName());
        dto.setPhone(entity.getPhone());
        dto.setTransportType(entity.getTransportType());
        dto.setLoadAssistance(entity.getLoadAssistance());

        return dto;
    }

    // Aggiorna un'entità esistente con i dati del DTO
    public void updateEntityFromDto(PickupRequestDto dto, PickupEntity entity) {
        if (dto == null || entity == null) {
            return;
        }

        if (dto.getPickupDate() != null) {
            entity.setPickupDate(LocalDate.parse(dto.getPickupDate()));
        }

        if (dto.getPickupTime() != null) {
            entity.setPickupTime(LocalTime.parse(dto.getPickupTime()));
        }

        if (dto.getFullName() != null) {
            entity.setFullName(dto.getFullName());
        }

        if (dto.getPhone() != null) {
            entity.setPhone(dto.getPhone());
        }

        if (dto.getTransportType() != null) {
            entity.setTransportType(dto.getTransportType());
        }

        if (dto.getLoadAssistance() != null) {
            entity.setLoadAssistance(dto.getLoadAssistance());
        }
    }
}
