package rw.mugaboandre.rentalhub.core.invoice.service;


import rw.mugaboandre.rentalhub.core.invoice.dto.InvoiceDto;
import rw.mugaboandre.rentalhub.core.invoice.dto.InvoiceResponseDto;
import rw.mugaboandre.rentalhub.core.invoice.model.Invoice;

import java.util.List;
import java.util.UUID;

public interface InvoiceService {
    InvoiceResponseDto createInvoice(InvoiceDto dto);
    InvoiceResponseDto getInvoiceById(UUID id);
    List<InvoiceResponseDto> getAllInvoices();
    InvoiceResponseDto updateInvoice(UUID id, InvoiceDto dto);
    void deleteInvoice(UUID id);
    Invoice getEntity(UUID id);

}
