package rw.mugaboandre.rentalhub.core.invoice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.mugaboandre.rentalhub.core.base.AbstractBaseEntity;
import rw.mugaboandre.rentalhub.core.client.model.Client;
import rw.mugaboandre.rentalhub.core.payment.model.Payment;
import rw.mugaboandre.rentalhub.core.property.model.Property;
import rw.mugaboandre.rentalhub.core.util.enums.invoice.EInvoiceStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Invoice extends AbstractBaseEntity {
    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EInvoiceStatus status;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Property property;

    @OneToMany(mappedBy = "invoice")
    private List<Payment> payments;


}
