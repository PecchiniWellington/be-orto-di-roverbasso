package com.ortoroverbasso.ortorovebasso.controller.orders.delivery_note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.orders.delivery_note.DeliveryNoteRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.orders.delivery_note.DeliveryNoteResponseDto;
import com.ortoroverbasso.ortorovebasso.service.orders.delivery_note.IDeliveryNoteService;

@RestController
@RequestMapping("/api/delivery-notes")
public class DeliveryNoteController {

    @Autowired
    private IDeliveryNoteService deliveryNoteService;

    @PostMapping("/{orderId}")
    public ResponseEntity<DeliveryNoteResponseDto> createDeliveryNote(
            @PathVariable Long orderId,
            @RequestBody DeliveryNoteRequestDto deliveryNoteRequestDto) {

        DeliveryNoteResponseDto response = deliveryNoteService.createDeliveryNote(orderId, deliveryNoteRequestDto);

        return ResponseEntity.ok(response);
    }
}
