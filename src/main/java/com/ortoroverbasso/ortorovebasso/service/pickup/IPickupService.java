package com.ortoroverbasso.ortorovebasso.service.pickup;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.pickup.PickupRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.pickup.PickupResponseDto;

public interface IPickupService {

    // Crea una nuova prenotazione
    PickupResponseDto createPickup(PickupRequestDto pickupRequestDto);

    // Ottieni tutte le prenotazioni
    List<PickupResponseDto> getAllPickups();

    // Ottieni una prenotazione per ID
    PickupResponseDto getPickupById(Long id);

    // Aggiorna una prenotazione esistente
    PickupResponseDto updatePickup(Long id, PickupRequestDto pickupRequestDto);

    // Elimina una prenotazione
    void deletePickup(Long id);
}
