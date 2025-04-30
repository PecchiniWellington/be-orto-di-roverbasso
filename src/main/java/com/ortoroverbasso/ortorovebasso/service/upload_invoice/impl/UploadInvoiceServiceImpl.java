package com.ortoroverbasso.ortorovebasso.service.upload_invoice.impl;

import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.upload_invoice.UploadInvoiceRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.upload_invoice.UploadInvoiceResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.upload_invoice.UploadInvoiceEntity;
import com.ortoroverbasso.ortorovebasso.service.upload_invoice.IUploadInvoiceService;

@Service
public class UploadInvoiceServiceImpl implements IUploadInvoiceService {

    @Override
    public UploadInvoiceResponseDto uploadInvoice(UploadInvoiceRequestDto dto) {
        // Mapping RequestDto to Entity
        UploadInvoiceEntity invoiceEntity = new UploadInvoiceEntity();
        invoiceEntity.setIdOrder(dto.getIdOrder());
        invoiceEntity.setFile(dto.getFile());
        invoiceEntity.setMimeType(dto.getMimeType());
        invoiceEntity.setAmount(dto.getAmount());
        invoiceEntity.setConcept(dto.getConcept());

        // Save the invoice to the database

        // Return response with success flag
        UploadInvoiceResponseDto response = new UploadInvoiceResponseDto();
        response.setSuccess(true); // Assuming the upload and save were successful

        return response;
    }
}
