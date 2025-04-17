package com.ortoroverbasso.ortorovebasso.entity.tags;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tags")
public class TagsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String linkRewrite;
    private String language;

    // Relazione OneToOne con ProductTagsEntity
    /*
     * mappedBy indica che la relazione è gestita dal campo "tag" in
     * ProductTagsEntity
     */
    @OneToOne(mappedBy = "tag")

    private ProductTagsEntity productTag;

    // Default constructor
    public TagsEntity() {
    }

    // All-args constructor
    public TagsEntity(Long id, String name, String linkRewrite, String language) {
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

    public ProductTagsEntity getProductTag() {
        return productTag;
    }

    public void setProductTag(ProductTagsEntity productTag) {
        this.productTag = productTag;
    }
}
