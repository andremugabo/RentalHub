package rw.mugaboandre.rentalhub.core.invoice.dto;


import lombok.Data;
import rw.mugaboandre.rentalhub.core.util.enums.invoice.EInvoiceStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class InvoiceDto {
    private BigDecimal amount;
    private LocalDate dueDate;
    private UUID clientId;
    private UUID propertyId;
}
