package com.ortoroverbasso.ortorovebasso.service.shipping;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.shipping.ProductCountryRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.ShippingCostByCountryResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.ShippingCostResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.ShippingServiceRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.ShippingServiceResponseDto;

public interface IShippingServiceService {
    List<ShippingServiceResponseDto> getAllShippingServices();

    ShippingServiceResponseDto getShippingServiceById(Long id);

    ShippingServiceResponseDto createShippingService(ShippingServiceRequestDto shippingServiceRequestDto);

    ShippingServiceResponseDto updateShippingService(Long id, ShippingServiceRequestDto shippingServiceRequestDto);

    void deleteShippingService(Long id);

    List<ShippingCostResponseDto> getLowestShippingCostByCountry(ProductCountryRequestDto productCountryRequest);

    List<ShippingCostByCountryResponseDto> getLowestShippingCostByCountry(String countryIsoCode);

}
