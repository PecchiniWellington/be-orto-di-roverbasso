package com.ortoroverbasso.ortorovebasso.service.product_information;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.ProductInformation.ProductInformationRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.ProductInformation.ProductInformationResponseDto;

public interface IProductInformationService {

    ProductInformationResponseDto getProductInformationBySku(String sku);

    ProductInformationResponseDto getProductInformationById(Long id);

    List<ProductInformationResponseDto> getAllProductInformation();

    List<ProductInformationResponseDto> getProductInformationByProductId(Long productId);

    ProductInformationResponseDto createProductInformation(ProductInformationRequestDto productInformation);

    ProductInformationResponseDto updateProductInformation(Long id, ProductInformationResponseDto productInformation);

    void deleteProductInformation(Long id);

}
