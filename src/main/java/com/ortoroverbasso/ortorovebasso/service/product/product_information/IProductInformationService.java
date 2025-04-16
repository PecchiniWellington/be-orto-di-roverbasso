package com.ortoroverbasso.ortorovebasso.service.product.product_information;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationResponseDto;

public interface IProductInformationService {

    ProductInformationResponseDto getProductInformationBySku(String sku);

    ProductInformationResponseDto getProductInformationById(Long id);

    List<ProductInformationResponseDto> getAllProductInformation();

    List<ProductInformationResponseDto> getProductInformationByProductId(Long productId);

    ProductInformationResponseDto createProductInformation(ProductInformationRequestDto productInformation);

    ProductInformationResponseDto updateProductInformation(Long id, ProductInformationResponseDto productInformation);

    void deleteProductInformation(Long id);

}
