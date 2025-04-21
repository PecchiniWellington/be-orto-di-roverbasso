package com.ortoroverbasso.ortorovebasso.entity.upload_invoice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UploadInvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idOrder;
    private String file;
    private String mimeType;
    private Double amount;
    private String concept;

    public UploadInvoiceEntity() {
    }

    public UploadInvoiceEntity(Long idOrder, String file, String mimeType, Double amount, String concept) {
        this.idOrder = idOrder;
        this.file = file;
        this.mimeType = mimeType;
        this.amount = amount;
        this.concept = concept;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

}
