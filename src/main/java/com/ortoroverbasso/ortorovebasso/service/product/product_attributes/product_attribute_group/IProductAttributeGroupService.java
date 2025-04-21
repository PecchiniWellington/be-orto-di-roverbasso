package com.ortoroverbasso.ortorovebasso.service.product.product_attributes.product_attribute_group;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.product.product_attributes.ProductAttributeGroupDto;

public interface IProductAttributeGroupService {

    List<ProductAttributeGroupDto> getAllAttributeGroups();

    ProductAttributeGroupDto getAttributeGroupById(Long id);
}
