package com.ortoroverbasso.ortorovebasso.service.product.product_attributes.product_attribute_group.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.product.product_attributes.ProductAttributeGroupDto;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_attribute.ProductAttributeGroupsMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.product_attributes.ProductAttributeGroupsRepository;
import com.ortoroverbasso.ortorovebasso.service.product.product_attributes.product_attribute_group.IProductAttributeGroupService;

@Service
public class ProductAttributeGroupsServiceImpl implements IProductAttributeGroupService {

    @Autowired
    private ProductAttributeGroupsRepository productAttributeGroupRepository;

    @Override
    public List<ProductAttributeGroupDto> getAllAttributeGroups() {
        return productAttributeGroupRepository.findAll().stream()
                .map(ProductAttributeGroupsMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductAttributeGroupDto getAttributeGroupById(Long id) {
        return productAttributeGroupRepository.findById(id)
                .map(ProductAttributeGroupsMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Attribute Group not found"));
    }
}
