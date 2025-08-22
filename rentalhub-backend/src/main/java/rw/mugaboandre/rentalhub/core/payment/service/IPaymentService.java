package rw.mugaboandre.rentalhub.core.payment.service;


import rw.mugaboandre.rentalhub.core.payment.dto.PaymentDto;
import rw.mugaboandre.rentalhub.core.payment.model.Payment;

import java.util.List;
import java.util.UUID;

public interface IPaymentService {

    Payment createPayment(PaymentDto dto);
    Payment getPaymentById(UUID id);
    List<Payment> getAllPayments();
    void deletePayment(UUID id);
    List<Payment> getPaymentsByInvoice(UUID invoiceId);
    List<Payment> getPaymentsByClient(UUID clientId);
}
