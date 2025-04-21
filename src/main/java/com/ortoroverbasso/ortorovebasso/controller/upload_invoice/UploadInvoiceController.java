package com.ortoroverbasso.ortorovebasso.controller.upload_invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.upload_invoice.UploadInvoiceRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.upload_invoice.UploadInvoiceResponseDto;
import com.ortoroverbasso.ortorovebasso.service.upload_invoice.IUploadInvoiceService;

@RestController
@RequestMapping("/api/upload_invoice")
public class UploadInvoiceController {

    @Autowired
    private IUploadInvoiceService uploadInvoiceService;

    @PostMapping("/upload_invoice")
    public ResponseEntity<UploadInvoiceResponseDto> uploadInvoice(
            @RequestBody UploadInvoiceRequestDto uploadInvoiceRequestDto) {

        // Calling service to handle the upload
        UploadInvoiceResponseDto response = uploadInvoiceService.uploadInvoice(uploadInvoiceRequestDto);

        return ResponseEntity.ok(response);
    }
}
