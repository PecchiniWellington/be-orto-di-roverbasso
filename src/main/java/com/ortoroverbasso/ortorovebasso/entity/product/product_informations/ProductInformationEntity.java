package com.ortoroverbasso.ortorovebasso.entity.product.product_informations;

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

@Table(name = "product_information")
@Entity
public class ProductInformationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String sku;
    private String name;
    private String description;
    private String url;
    private String isoCode;
    private String dateUpdDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id") // Assicurati che la colonna si chiami 'product_id'
    private ProductEntity product;

    // Default constructor
    public ProductInformationEntity() {
    }

    // All-args constructor
    public ProductInformationEntity(
            Long id,
            String sku,
            String name,
            String description,
            String url,
            String isoCode,
            String dateUpdDescription,
            ProductEntity product) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.url = url;
        this.isoCode = isoCode;
        this.dateUpdDescription = dateUpdDescription;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getDateUpdDescription() {
        return dateUpdDescription;
    }

    public void setDateUpdDescription(String dateUpdDescription) {
        this.dateUpdDescription = dateUpdDescription;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    // toString method
    @Override
    public String toString() {
        return "ProductInformationEntity{" +
                "id=" + id +
                ", sku='" + sku + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", isoCode='" + isoCode + '\'' +
                ", dateUpdDescription='" + dateUpdDescription + '\'' +
                '}';
    }

    // equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true; // Se sono lo stesso oggetto, restituisci true.
        if (o == null || getClass() != o.getClass())
            return false; // Se l'oggetto è null o non è della stessa classe, restituisci false.

        ProductInformationEntity that = (ProductInformationEntity) o;

        // Confronta tutti i campi significativi
        if (id != null ? !id.equals(that.id) : that.id != null)
            return false;
        if (sku != null ? !sku.equals(that.sku) : that.sku != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (url != null ? !url.equals(that.url) : that.url != null)
            return false;
        if (isoCode != null ? !isoCode.equals(that.isoCode) : that.isoCode != null)
            return false;
        if (dateUpdDescription != null ? !dateUpdDescription.equals(that.dateUpdDescription)
                : that.dateUpdDescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (sku != null ? sku.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (isoCode != null ? isoCode.hashCode() : 0);
        result = 31 * result + (dateUpdDescription != null ? dateUpdDescription.hashCode() : 0);
        return result;
    }
}
