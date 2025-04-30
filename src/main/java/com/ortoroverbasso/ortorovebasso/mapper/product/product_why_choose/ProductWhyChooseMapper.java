package com.ortoroverbasso.ortorovebasso.mapper.product.product_why_choose;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ortoroverbasso.ortorovebasso.dto.product.product_why_choose.ProductWhyChooseRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_why_choose.ProductWhyChooseResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_why_choose.ProductWhyChooseResponseNoProductIdDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_why_choose.ProductWhyChooseEntity;

@Component
public class ProductWhyChooseMapper {

    public static ProductWhyChooseResponseDto toResponse(ProductWhyChooseEntity entity) {
        ProductWhyChooseResponseDto response = new ProductWhyChooseResponseDto();
        response.setId(entity.getId());
        response.setValue(entity.getValue());
        response.setProductIds(entity.getProducts().stream()
                .map(ProductEntity::getId)
                .collect(Collectors.toList()));
        return response;
    }

    public static ProductWhyChooseResponseNoProductIdDto toResponseWithoutProductId(ProductWhyChooseEntity entity) {
        ProductWhyChooseResponseNoProductIdDto response = new ProductWhyChooseResponseNoProductIdDto();
        response.setId(entity.getId());
        response.setValue(entity.getValue());
        return response;
    }

    public static ProductWhyChooseEntity toEntity(ProductWhyChooseRequestDto request) {
        return new ProductWhyChooseEntity(request.getValue());
    }
}
