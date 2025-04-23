package com.ortoroverbasso.ortorovebasso.dto.tags;

public class ProductTagsResponseDto {

    private Long id;
    private String sku;
    private TagsResponseDto tag;

    // Constructor
    public ProductTagsResponseDto(Long id, String sku, TagsResponseDto tag) {
        this.id = id;
        this.sku = sku;
        this.tag = tag;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public TagsResponseDto getTag() {
        return tag;
    }

    public void setTag(TagsResponseDto tag) {
        this.tag = tag;
    }
}
