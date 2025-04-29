
package com.ortoroverbasso.ortorovebasso.controller.product.product_tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.tags.ProductTagsRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.tags.ProductTagsResponseDto;
import com.ortoroverbasso.ortorovebasso.service.tags.IProductTagsService;

@RestController
@RequestMapping("/api/products/{productId}/tags")
public class ProductTagController {

    @Autowired
    private IProductTagsService productTagsService;

    @GetMapping()
    public ProductTagsResponseDto getProductTags(@PathVariable Long productId) {
        return productTagsService.getProductTagsById(productId);
    }

    @PostMapping()
    public ProductTagsResponseDto createProductTag(
            @PathVariable Long productId,
            @RequestBody ProductTagsRequestDto productTagsRequestDto) {
        return productTagsService.createProductTag(productTagsRequestDto);
    }

    @DeleteMapping("/{tagId}")

    public void deleteProductTag(@PathVariable Long productId, @PathVariable Long tagId) {
        productTagsService.deleteProductTag(tagId);
    }

}
