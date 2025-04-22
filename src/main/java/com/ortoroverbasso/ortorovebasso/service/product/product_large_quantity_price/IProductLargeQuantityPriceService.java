package com.ortoroverbasso.ortorovebasso.service.product.product_large_quantity_price;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceResponseDto;

public interface IProductLargeQuantityPriceService {

    List<ProductLargeQuantityPriceResponseDto> getProductLargeQuantityPriceByProductId(Long productId);

    List<ProductLargeQuantityPriceResponseDto> getProductLargeQuantityPrice();

    ProductLargeQuantityPriceResponseDto createProductPriceLargeQuantity(
            ProductLargeQuantityPriceRequestDto productPriceLargeQuantityRequestDto);

}
