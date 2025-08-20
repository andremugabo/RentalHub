package rw.mugaboandre.rentalhub.core.payment.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.mugaboandre.rentalhub.core.base.AbstractBaseEntity;
import rw.mugaboandre.rentalhub.core.client.model.Client;
import rw.mugaboandre.rentalhub.core.invoice.model.Invoice;
import rw.mugaboandre.rentalhub.core.util.enums.payment.EPaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
public class Payment extends AbstractBaseEntity {
    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "date")
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    private EPaymentMethod method;

    @Column(name = "reference_no")
    private String referenceNo;

    @ManyToOne
    private Invoice invoice;

    @ManyToOne
    private Client client;


}