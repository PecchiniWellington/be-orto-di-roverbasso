package com.ortoroverbasso.ortorovebasso.service.product.product_information;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationResponseDto;

public interface IProductInformationService {

    ProductInformationResponseDto getProductInformationBySku(String sku);

    ProductInformationResponseDto getProductInformation(Long productId);

    List<ProductInformationResponseDto> getProductInformationByProductId(Long productId);

    ProductInformationResponseDto createProductInformation(Long productId,
            ProductInformationRequestDto productInformation);

    ProductInformationResponseDto updateProductInformation(Long id, ProductInformationRequestDto productInformation);

    void deleteProductInformation(Long id);

}
