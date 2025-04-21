package com.ortoroverbasso.ortorovebasso.service.orders.delivery_note;

import com.ortoroverbasso.ortorovebasso.dto.orders.delivery_note.DeliveryNoteRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.orders.delivery_note.DeliveryNoteResponseDto;

public interface IDeliveryNoteService {
    DeliveryNoteResponseDto createDeliveryNote(Long orderId, DeliveryNoteRequestDto dto);
}
