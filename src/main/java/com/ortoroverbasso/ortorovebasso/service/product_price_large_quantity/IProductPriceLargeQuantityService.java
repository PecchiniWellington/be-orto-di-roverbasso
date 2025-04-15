package com.ortoroverbasso.ortorovebasso.service.product_price_large_quantity;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.product_price_large_quantity.ProductPriceLargeQuantityRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product_price_large_quantity.ProductPriceLargeQuantityResponseDto;

public interface IProductPriceLargeQuantityService {

    List<ProductPriceLargeQuantityResponseDto> getProductPricesLargeQuantityByProductId(Long productId);

    ProductPriceLargeQuantityResponseDto createProductPriceLargeQuantity(Long productId,
            ProductPriceLargeQuantityRequestDto productPriceLargeQuantityRequestDto);

}
