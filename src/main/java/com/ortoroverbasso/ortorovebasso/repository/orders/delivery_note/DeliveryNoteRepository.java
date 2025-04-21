package com.ortoroverbasso.ortorovebasso.repository.orders.delivery_note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.order.delivery_note.DeliveryNoteEntity;

@Repository
public interface DeliveryNoteRepository extends JpaRepository<DeliveryNoteEntity, Long> {
}
