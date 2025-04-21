package com.ortoroverbasso.ortorovebasso.repository.upload_invoice;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ortoroverbasso.ortorovebasso.entity.upload_invoice.UploadInvoiceEntity;

public interface UploadInvoiceRepository extends JpaRepository<UploadInvoiceEntity, Long> {
    // Puoi aggiungere query personalizzate se necessario
}
