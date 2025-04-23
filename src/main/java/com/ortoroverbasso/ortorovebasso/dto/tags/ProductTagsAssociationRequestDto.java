package com.ortoroverbasso.ortorovebasso.dto.tags;

import java.util.List;

public class ProductTagsAssociationRequestDto {

    private List<Long> tagIds;

    // Costruttori
    public ProductTagsAssociationRequestDto() {
    }

    public ProductTagsAssociationRequestDto(List<Long> tagIds) {
        this.tagIds = tagIds;
    }

    // Getter e Setter
    public List<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
    }
}
