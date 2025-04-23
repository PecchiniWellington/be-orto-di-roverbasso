package com.ortoroverbasso.ortorovebasso.dto.tags;

import java.util.List;

public class ProductTagsRequestDto {

    private Long productId;
    private List<Long> tagIds;

    // Costruttori
    public ProductTagsRequestDto() {
    }

    public ProductTagsRequestDto(Long productId, List<Long> tagIds) {
        this.productId = productId;
        this.tagIds = tagIds;
    }

    // Getter e Setter
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
    }
}
