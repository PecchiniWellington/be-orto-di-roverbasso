package com.ortoroverbasso.ortorovebasso.dto.tags;

public class ProductTagsRequestDto {

    private Long id;
    private String sku;
    private TagsRequestDto tag; // Utilizziamo il DTO di Tags

    // Default constructor
    public ProductTagsRequestDto() {
    }

    // All-args constructor
    public ProductTagsRequestDto(
            Long id,
            String sku,
            TagsRequestDto tag) {
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

    public TagsRequestDto getTag() {
        return tag;
    }

    public void setTag(TagsRequestDto tag) {
        this.tag = tag;
    }
}
