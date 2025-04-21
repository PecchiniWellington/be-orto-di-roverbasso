package com.ortoroverbasso.ortorovebasso.service.orders.delivery_note.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.orders.delivery_note.DeliveryNoteRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.orders.delivery_note.DeliveryNoteResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.order.delivery_note.DeliveryNoteEntity;
import com.ortoroverbasso.ortorovebasso.mapper.orders.delivery_note.DeliveryNoteMapper;
import com.ortoroverbasso.ortorovebasso.repository.orders.delivery_note.DeliveryNoteRepository;
import com.ortoroverbasso.ortorovebasso.service.orders.delivery_note.IDeliveryNoteService;

@Service
public class DeliveryNoteServiceImpl implements IDeliveryNoteService {

    @Autowired
    private DeliveryNoteRepository deliveryNoteRepository;

    @Override
    public DeliveryNoteResponseDto createDeliveryNote(Long orderId, DeliveryNoteRequestDto dto) {
        // Mapping RequestDto to Entity
        DeliveryNoteEntity entity = DeliveryNoteMapper.toEntity(dto);

        // Save the delivery note
        deliveryNoteRepository.save(entity);

        // Return success response
        return DeliveryNoteMapper.toResponse(entity);
    }
}
