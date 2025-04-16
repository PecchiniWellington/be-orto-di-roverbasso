package com.ortoroverbasso.ortorovebasso.service.product.product_large_quantity_price;

import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceResponseDto;

public interface IProductLargeQuantityPriceService {

    ProductLargeQuantityPriceResponseDto createProductPriceLargeQuantity(Long productId,
            ProductLargeQuantityPriceRequestDto productPriceLargeQuantityRequestDto);

}
