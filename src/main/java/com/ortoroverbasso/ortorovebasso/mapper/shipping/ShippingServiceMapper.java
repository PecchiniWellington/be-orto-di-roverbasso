package com.ortoroverbasso.ortorovebasso.mapper.shipping;

import java.util.List;
import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.shipping.ExcludedCategoryIdsResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.ExcludedProductReferenceResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.ShippingCountryResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.ShippingServiceRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.ShippingServiceResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.shipping.ExcludedCategoryIdsEntity;
import com.ortoroverbasso.ortorovebasso.entity.shipping.ExcludedProductReferenceEntity;
import com.ortoroverbasso.ortorovebasso.entity.shipping.ShippingCountryEntity;
import com.ortoroverbasso.ortorovebasso.entity.shipping.ShippingServiceEntity;

public class ShippingServiceMapper {

        public static ShippingServiceEntity toEntity(ShippingServiceRequestDto dto) {
                ShippingServiceEntity entity = new ShippingServiceEntity();
                entity.setName(dto.getName());
                entity.setDelay(dto.getDelay());
                entity.setPod(dto.getPod());

                // Mapping countries
                List<ShippingCountryEntity> countries = dto.getShippingCountries().stream()
                                .map(countryDto -> new ShippingCountryEntity(countryDto.getIsoCountry())) // Passiamo il
                                                                                                          // riferimento
                                                                                                          // corretto
                                .collect(Collectors.toList());
                entity.setShippingCountries(countries);

                List<ExcludedProductReferenceEntity> excludedProducts = dto.getExcludedProductReferences().stream()
                                .flatMap(list -> list.stream())
                                .map(reference -> new ExcludedProductReferenceEntity(reference.getReference()))
                                .collect(Collectors.toList());
                entity.setExcludedProductReferences(excludedProducts);

                List<ExcludedCategoryIdsEntity> excludedCategoryIds = dto.getExcludedCategoryIds().stream()
                                .flatMap(list -> list.stream())
                                .map(categoryIds -> new ExcludedCategoryIdsEntity(categoryIds.getCategoryIds()))
                                .collect(Collectors.toList());
                entity.setExcludedCategoryIds(excludedCategoryIds);

                return entity;
        }

        public static ShippingServiceResponseDto toResponse(ShippingServiceEntity entity) {
                ShippingServiceResponseDto dto = new ShippingServiceResponseDto();
                dto.setId(entity.getId());
                dto.setName(entity.getName());
                dto.setDelay(entity.getDelay());
                dto.setPod(entity.getPod());

                List<ShippingCountryResponseDto> countries = entity.getShippingCountries().stream()
                                .map(countryEntity -> new ShippingCountryResponseDto(countryEntity.getIsoCountry()))
                                .collect(Collectors.toList());
                dto.setShippingCountries(countries);

                List<List<ExcludedProductReferenceResponseDto>> excludedProducts = entity.getExcludedProductReferences()
                                .stream()
                                .map(excluded -> List
                                                .of(new ExcludedProductReferenceResponseDto(excluded.getReference())))
                                .collect(Collectors.toList());
                dto.setExcludedProductReferences(excludedProducts);

                List<List<ExcludedCategoryIdsResponseDto>> excludedCategoryIds = entity.getExcludedCategoryIds()
                                .stream()
                                .map(excluded -> List
                                                .of(new ExcludedCategoryIdsResponseDto(excluded.getCategoryIds())))
                                .collect(Collectors.toList());
                dto.setExcludedCategoryIds(excludedCategoryIds);

                return dto;
        }
}
