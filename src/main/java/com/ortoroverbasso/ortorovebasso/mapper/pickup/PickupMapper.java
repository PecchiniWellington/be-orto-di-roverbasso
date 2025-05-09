package com.ortoroverbasso.ortorovebasso.mapper.pickup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ortoroverbasso.ortorovebasso.dto.pickup.PickupRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.pickup.PickupResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.pickup.PickupEntity;

@Component
public class PickupMapper {
    private static final Logger logger = LoggerFactory.getLogger(PickupMapper.class);

    public static PickupResponseDto toDto(PickupEntity entity) {
        if (entity == null) {
            return null;
        }

        PickupResponseDto dto = new PickupResponseDto();
        dto.setId(entity.getId());

        // Convert LocalDate to String
        if (entity.getPickupDate() != null) {
            dto.setPickupDate(entity.getPickupDate());
        }

        // Convert LocalTime to String
        if (entity.getPickupTime() != null) {
            dto.setPickupTime(entity.getPickupTime());
        }

        dto.setFullName(entity.getFullName());
        dto.setPhone(entity.getPhone());
        dto.setTransportType(entity.getTransportType());
        dto.setLoadAssistance(entity.getLoadAssistance());
        dto.setToken(entity.getToken());

        return dto;
    }

    public static PickupEntity toEntity(PickupRequestDto dto) {
        if (dto == null) {
            return null;
        }

        logger.debug("Converting DTO to entity. Token in DTO: {}", dto.getToken());

        PickupEntity entity = new PickupEntity();

        // Convert String to LocalDate
        if (dto.getPickupDate() != null) {
            entity.setPickupDate(dto.getPickupDate());
        }

        // Convert String to LocalTime
        if (dto.getPickupTime() != null) {
            entity.setPickupTime(dto.getPickupTime());
        }

        entity.setFullName(dto.getFullName());
        entity.setPhone(dto.getPhone());
        entity.setTransportType(dto.getTransportType());
        entity.setLoadAssistance(dto.getLoadAssistance());

        // Always set token without null check - frontend is sending it

        System.out.println("Token in DTO: " + dto.getToken());
        entity.setToken(dto.getToken());
        logger.debug("Token set in entity: {}", entity.getToken());

        return entity;
    }

    /**
     * Updates an existing PickupEntity with values from a PickupRequestDto
     */
    public static void updateEntityFromDto(PickupRequestDto dto, PickupEntity entity) {
        if (dto == null || entity == null) {
            return;
        }

        logger.debug("Updating entity from DTO. Token in DTO: {}", dto.getToken());

        // Convert String to LocalDate
        if (dto.getPickupDate() != null) {
            entity.setPickupDate(dto.getPickupDate());
        }

        // Convert String to LocalTime
        if (dto.getPickupTime() != null) {
            entity.setPickupTime(dto.getPickupTime());
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

        System.out.println("Token in DTO: " + dto.getToken());
        // Always update token without null check
        entity.setToken(dto.getToken());
        logger.debug("Token updated in entity: {}", entity.getToken());
    }
}
