package com.ortoroverbasso.ortorovebasso.entity.images;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "images")
public class ImagesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "imagesEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ImagesDetailEntity> images;

    public ImagesEntity() {
    }

    public ImagesEntity(long id, List<ImagesDetailEntity> images) {
        this.id = id;
        this.images = images;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ImagesDetailEntity> getImages() {
        return images;
    }

    public void setImages(List<ImagesDetailEntity> images) {
        this.images = images;
    }
}
