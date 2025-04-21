package com.ortoroverbasso.ortorovebasso.service.upload_invoice;

import com.ortoroverbasso.ortorovebasso.dto.upload_invoice.UploadInvoiceRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.upload_invoice.UploadInvoiceResponseDto;

public interface IUploadInvoiceService {
    UploadInvoiceResponseDto uploadInvoice(UploadInvoiceRequestDto dto);
}
