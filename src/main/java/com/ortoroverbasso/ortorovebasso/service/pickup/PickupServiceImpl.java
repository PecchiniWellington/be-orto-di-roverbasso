package com.ortoroverbasso.ortorovebasso.service.pickup;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.pickup.PickupRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.pickup.PickupResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.pickup.PickupEntity;
import com.ortoroverbasso.ortorovebasso.mapper.PickupMapper;
import com.ortoroverbasso.ortorovebasso.repository.PickupRepository;

@Service
public class PickupServiceImpl implements IPickupService {

    private final PickupRepository pickupRepository;
    private final PickupMapper pickupMapper;

    @Autowired
    public PickupServiceImpl(PickupRepository pickupRepository, PickupMapper pickupMapper) {
        this.pickupRepository = pickupRepository;
        this.pickupMapper = pickupMapper;
    }

    @Override
    public PickupResponseDto createPickup(PickupRequestDto pickupRequestDto) {
        PickupEntity pickupEntity = pickupMapper.toEntity(pickupRequestDto);
        PickupEntity savedEntity = pickupRepository.save(pickupEntity);
        return pickupMapper.toDto(savedEntity);
    }

    @Override
    public List<PickupResponseDto> getAllPickups() {
        List<PickupEntity> entities = pickupRepository.findAll();
        return entities.stream()
                .map(pickupMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PickupResponseDto getPickupById(Long id) {
        PickupEntity entity = pickupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione con ID " + id + " non trovata"));
        return pickupMapper.toDto(entity);
    }

    @Override
    public PickupResponseDto updatePickup(Long id, PickupRequestDto pickupRequestDto) {
        PickupEntity existingEntity = pickupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione con ID " + id + " non trovata"));

        pickupMapper.updateEntityFromDto(pickupRequestDto, existingEntity);
        PickupEntity updatedEntity = pickupRepository.save(existingEntity);

        return pickupMapper.toDto(updatedEntity);
    }

    @Override
    public void deletePickup(Long id) {
        if (!pickupRepository.existsById(id)) {
            throw new RuntimeException("Prenotazione con ID " + id + " non trovata");
        }
        pickupRepository.deleteById(id);
    }
}
