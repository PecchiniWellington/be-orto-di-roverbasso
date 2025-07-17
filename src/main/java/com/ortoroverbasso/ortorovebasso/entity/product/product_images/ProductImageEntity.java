package com.ortoroverbasso.ortorovebasso.entity.product.product_images;

import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "product_images")
public class ProductImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Image URL is required")
    @Column(name = "url", nullable = false)
    private String url;

    @NotNull(message = "Cover status is required")
    @Column(name = "is_cover", nullable = false)
    private Boolean isCover = false;

    @Column(name = "alt_text")
    private String altText;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    // Constructors
    public ProductImageEntity() {
    }

    public ProductImageEntity(String url, Boolean isCover) {
        this.url = url;
        this.isCover = isCover;
    }

    public ProductImageEntity(String url, Boolean isCover, String altText) {
        this.url = url;
        this.isCover = isCover;
        this.altText = altText;
    }

    public void markAsCover() {
        this.isCover = true;
    }

    public void unmarkAsCover() {
        this.isCover = false;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getIsCover() {
        return isCover;
    }

    public void setIsCover(Boolean isCover) {
        this.isCover = isCover;
    }

    // Metodo per compatibilità (getter alternativo)
    public boolean isCover() {
        return Boolean.TRUE.equals(isCover);
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ProductImageEntity that = (ProductImageEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null)
            return false;
        return url != null ? url.equals(that.url) : that.url == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductImageEntity{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", isCover=" + isCover +
                ", altText='" + altText + '\'' +
                ", displayOrder=" + displayOrder +
                '}';
    }
}
