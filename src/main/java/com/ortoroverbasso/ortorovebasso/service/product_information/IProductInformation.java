package com.ortoroverbasso.ortorovebasso.service.product_information;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.ProductInformation.ProductInformationResponseDto;

public interface IProductInformation {

    ProductInformationResponseDto getProductInformationById(Long id);

    List<ProductInformationResponseDto> getAllProductInformation();

    List<ProductInformationResponseDto> getProductInformationByProductId(Long productId);

    ProductInformationResponseDto createProductInformation(ProductInformationResponseDto productInformation);

    ProductInformationResponseDto updateProductInformation(Long id, ProductInformationResponseDto productInformation);

    void deleteProductInformation(Long id);

}
