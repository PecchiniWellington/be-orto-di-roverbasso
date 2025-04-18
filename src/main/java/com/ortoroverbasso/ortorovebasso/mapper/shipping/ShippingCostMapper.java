package com.ortoroverbasso.ortorovebasso.mapper.shipping;

import com.ortoroverbasso.ortorovebasso.dto.shipping.CarrierResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.ShippingCostByCountryResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.shipping.ShippingCostEntity;

public class ShippingCostMapper {

    public static ShippingCostByCountryResponseDto toResponse(ShippingCostEntity entity) {
        CarrierResponseDto carrier = new CarrierResponseDto(entity.getCarrierId(), entity.getCarrierName());
        return new ShippingCostByCountryResponseDto(
                entity.getReference(),
                entity.getCost(),
                carrier);
    }
}
