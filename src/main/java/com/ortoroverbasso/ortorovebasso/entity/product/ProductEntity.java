package com.ortoroverbasso.ortorovebasso.entity.product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long manufacturer;
    private String sku;
    private String ean13;
    private Integer weight;
    private Integer height;
    private Integer width;
    private Integer depth;
    private LocalDateTime dateUpd;
    private Long category;
    private LocalDateTime dateUpdDescription;
    private LocalDateTime dateUpdImages;
    private LocalDateTime dateUpdStock;
    private String wholesalePrice;
    private String retailPrice;
    private Long taxonomy;
    private LocalDateTime dateAdd;
    private String video;
    private Integer active;
    private Boolean attributes;
    private Boolean categories;
    private Boolean images;
    private Integer taxRate;
    private Integer taxId;
    private Double inShopsPrice;
    private String condition;
    private String logisticClass;
    private Boolean tags;
    private LocalDateTime dateUpdProperties;
    private LocalDateTime dateUpdCategories;
    private String intrastat;
    private String partNumber;
    private Double canon;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PriceLargeQuantityEntity> priceLargeQuantities = new ArrayList<>();

}
