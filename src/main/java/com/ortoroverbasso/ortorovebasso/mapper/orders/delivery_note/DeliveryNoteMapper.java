package com.ortoroverbasso.ortorovebasso.mapper.orders.delivery_note;

import com.ortoroverbasso.ortorovebasso.dto.orders.delivery_note.DeliveryNoteRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.orders.delivery_note.DeliveryNoteResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.order.delivery_note.DeliveryNoteEntity;

public class DeliveryNoteMapper {

    public static DeliveryNoteEntity toEntity(DeliveryNoteRequestDto dto) {
        DeliveryNoteEntity entity = new DeliveryNoteEntity();
        entity.setReference(dto.getReference());
        entity.setOrder(dto.getOrder());
        entity.setStatus(dto.getStatus());
        entity.setCarrier(dto.getCarrier());
        entity.setNumberOfPackages(dto.getNumberOfPackages());

        // Return the entity
        return entity;
    }

    public static DeliveryNoteResponseDto toResponse(DeliveryNoteEntity entity) {
        DeliveryNoteResponseDto dto = new DeliveryNoteResponseDto();
        dto.setSuccess(true); // assuming the operation is successful

        return dto;
    }
}
