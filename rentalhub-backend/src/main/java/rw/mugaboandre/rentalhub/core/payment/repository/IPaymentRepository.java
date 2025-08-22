package rw.mugaboandre.rentalhub.core.payment.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.mugaboandre.rentalhub.core.payment.model.Payment;

import java.util.List;
import java.util.UUID;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findByClientId(UUID clientId);
    List<Payment> findByInvoiceId(UUID invoiceId);
}
