package com.ortoroverbasso.ortorovebasso.dto.tags;

public class TagsResponseDto {

    private Long id;
    private String name;
    private String linkRewrite;
    private String language;

    // Default constructor
    public TagsResponseDto() {
    }

    // All-args constructor
    public TagsResponseDto(
            Long id,
            String name,
            String linkRewrite,
            String language) {
        this.id = id;
        this.name = name;
        this.linkRewrite = linkRewrite;
        this.language = language;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkRewrite() {
        return linkRewrite;
    }

    public void setLinkRewrite(String linkRewrite) {
        this.linkRewrite = linkRewrite;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
