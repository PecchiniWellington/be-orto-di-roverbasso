package com.ortoroverbasso.ortorovebasso.service.product.product_attributes.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.product.product_attributes.ProductAttributesRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_attributes.ProductAttributesResponseDto;
import com.ortoroverbasso.ortorovebasso.service.product.product_attributes.IProductAttributesService;

@Service
public class ProductAttributesServiceImpl implements IProductAttributesService {

    @Override
    public List<ProductAttributesResponseDto> getProductAttributesByProductId(Long productId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductAttributesByProductId'");
    }

    @Override
    public ProductAttributesResponseDto saveProductAttributes(ProductAttributesRequestDto productAttributesRequestDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveProductAttributes'");
    }

    @Override
    public void deleteProductAttributes(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteProductAttributes'");
    }

    @Override
    public ProductAttributesResponseDto getProductAttributesById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductAttributesById'");
    }

    @Override
    public List<ProductAttributesResponseDto> getAllProductAttributes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllProductAttributes'");
    }

}
