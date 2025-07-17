package com.ortoroverbasso.ortorovebasso.entity.category;

import java.util.HashSet;
import java.util.Set;

import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id", nullable = true)
    private CategoryEntity parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CategoryEntity> subCategories = new HashSet<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductEntity> products = new HashSet<>();

    // Costruttori
    public CategoryEntity() {
    }

    public CategoryEntity(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }

    // Getter e setter
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

    public CategoryEntity getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(CategoryEntity parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Set<CategoryEntity> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Set<CategoryEntity> subCategories) {
        this.subCategories = subCategories;
    }

    public Set<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductEntity> products) {
        this.products = products;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    // Metodi di utilità
    public void addSubCategory(CategoryEntity subCategory) {
        subCategories.add(subCategory);
        subCategory.setParentCategory(this);
    }

    public void removeSubCategory(CategoryEntity subCategory) {
        subCategories.remove(subCategory);
        subCategory.setParentCategory(null);
    }

    public void addProduct(ProductEntity product) {
        products.add(product);
        product.setCategory(this);
    }

    public void removeProduct(ProductEntity product) {
        products.remove(product);
        product.setCategory(null);
    }

    public boolean isRootCategory() {
        return parentCategory == null;
    }

    public boolean hasSubCategories() {
        return !subCategories.isEmpty();
    }

    public boolean hasProducts() {
        return !products.isEmpty();
    }
}
