package rw.mugaboandre.rentalhub.core.payment.dto;


import lombok.Data;
import rw.mugaboandre.rentalhub.core.util.enums.payment.EPaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PaymentDto {
    private BigDecimal amount;
    private LocalDateTime date;
    private EPaymentMethod method;
    private String referenceNo;
    private UUID invoiceId;
    private UUID clientId;
}
