package com.ortoroverbasso.ortorovebasso.controller.product.product_attributes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.product.product_attributes.ProductAttributeGroupDto;
import com.ortoroverbasso.ortorovebasso.service.product.product_attributes.product_attribute_group.IProductAttributeGroupService;

@RestController
@RequestMapping("/api/attribute-groups")
public class ProductAttributeGroupsController {

    @Autowired
    private IProductAttributeGroupService productAttributeGroupService;

    // Endpoint per ottenere tutti gli attribute groups
    @GetMapping
    public ResponseEntity<List<ProductAttributeGroupDto>> getAllAttributeGroups() {
        List<ProductAttributeGroupDto> attributeGroups = productAttributeGroupService.getAllAttributeGroups();
        return ResponseEntity.ok(attributeGroups);
    }

    // Endpoint per ottenere un singolo attribute group per ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductAttributeGroupDto> getAttributeGroupById(@PathVariable Long id) {
        ProductAttributeGroupDto attributeGroup = productAttributeGroupService.getAttributeGroupById(id);
        return ResponseEntity.ok(attributeGroup);
    }
}
