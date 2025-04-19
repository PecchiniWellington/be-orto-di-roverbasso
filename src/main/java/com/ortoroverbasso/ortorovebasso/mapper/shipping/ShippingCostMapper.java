package com.ortoroverbasso.ortorovebasso.mapper.shipping;

import com.ortoroverbasso.ortorovebasso.dto.shipping.CarrierResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.ShippingCostResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.shipping.ShippingCostEntity;

public class ShippingCostMapper {

    public static ShippingCostResponseDto toResponse(ShippingCostEntity entity) {
        CarrierResponseDto carrierDto = new CarrierResponseDto();
        carrierDto.setId(entity.getCarrier().getId());
        carrierDto.setName(entity.getCarrier().getName());

        return new ShippingCostResponseDto(entity.getCost(), carrierDto);
    }
}
