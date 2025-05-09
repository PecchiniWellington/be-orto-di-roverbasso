package com.ortoroverbasso.ortorovebasso.controller.pickup;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.pickup.PickupRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.pickup.PickupResponseDto;
import com.ortoroverbasso.ortorovebasso.service.pickup.IPickupService;

@RestController
@RequestMapping("/api/pickups")
public class PickupController {

    private static final Logger logger = LoggerFactory.getLogger(PickupController.class);
    private final IPickupService pickupService;

    @Autowired
    public PickupController(IPickupService pickupService) {
        this.pickupService = pickupService;
    }

    @PostMapping
    public ResponseEntity<PickupResponseDto> createPickup(@RequestBody PickupRequestDto pickupRequestDto) {
        logger.info("Received pickup request:");
        logger.info("Token: {}", pickupRequestDto.getToken());
        logger.info("CartToken: {}", pickupRequestDto.getCartToken());
        logger.info("FullName: {}", pickupRequestDto.getFullName());
        logger.info("PickupDate: {}", pickupRequestDto.getPickupDate());

        PickupResponseDto createdPickup = pickupService.createPickup(pickupRequestDto);
        return new ResponseEntity<>(createdPickup, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PickupResponseDto>> getAllPickups() {
        List<PickupResponseDto> pickups = pickupService.getAllPickups();
        return new ResponseEntity<>(pickups, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PickupResponseDto> getPickupById(@PathVariable Long id) {
        PickupResponseDto pickup = pickupService.getPickupById(id);
        return new ResponseEntity<>(pickup, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PickupResponseDto> updatePickup(
            @PathVariable Long id,
            @RequestBody PickupRequestDto pickupRequestDto) {
        PickupResponseDto updatedPickup = pickupService.updatePickup(id, pickupRequestDto);
        return new ResponseEntity<>(updatedPickup, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePickup(@PathVariable Long id) {
        pickupService.deletePickup(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
