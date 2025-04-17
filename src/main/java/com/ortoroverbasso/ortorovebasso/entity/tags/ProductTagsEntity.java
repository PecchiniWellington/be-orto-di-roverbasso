package com.ortoroverbasso.ortorovebasso.entity.tags;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_tags")
public class ProductTagsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;

    // Relazione OneToOne con TagsEntity
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private TagsEntity tag;

    // Default constructor
    public ProductTagsEntity() {
    }

    // All-args constructor
    public ProductTagsEntity(Long id, String sku, TagsEntity tag) {
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

    public TagsEntity getTag() {
        return tag;
    }

    public void setTag(TagsEntity tag) {
        this.tag = tag;
    }
}
