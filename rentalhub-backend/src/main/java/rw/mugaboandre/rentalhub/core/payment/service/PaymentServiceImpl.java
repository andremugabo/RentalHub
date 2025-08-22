package rw.mugaboandre.rentalhub.core.payment.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.mugaboandre.rentalhub.core.client.repository.IClientRepository;
import rw.mugaboandre.rentalhub.core.invoice.repository.InvoiceRepository;
import rw.mugaboandre.rentalhub.core.payment.dto.PaymentDto;
import rw.mugaboandre.rentalhub.core.payment.model.Payment;
import rw.mugaboandre.rentalhub.core.payment.repository.IPaymentRepository;
import rw.mugaboandre.rentalhub.core.notification.service.INotificationService;
import rw.mugaboandre.rentalhub.core.util.enums.notifications.ENotificationType;
import rw.mugaboandre.rentalhub.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements IPaymentService {

    private final IPaymentRepository paymentRepository;
    private final IClientRepository clientRepository;
    private final InvoiceRepository invoiceRepository;
    private final INotificationService notificationService;

    @Override
    public Payment createPayment(PaymentDto dto) {
        Payment payment = new Payment();
        payment.setAmount(dto.getAmount());
        payment.setDate(dto.getDate() != null ? dto.getDate() : java.time.LocalDateTime.now());
        payment.setMethod(dto.getMethod());
        payment.setReferenceNo(dto.getReferenceNo());

        var client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with ID " + dto.getClientId()));
        payment.setClient(client);

        var invoice = invoiceRepository.findById(dto.getInvoiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with ID " + dto.getInvoiceId()));
        payment.setInvoice(invoice);

        Payment saved = paymentRepository.save(payment);

        // Notify client
        notificationService.createNotification(
                client,
                "Payment of " + saved.getAmount() + " has been successfully processed.",
                ENotificationType.RENT_PAYMENT_SUCCESS
        );

        // Notify property owner
        if (invoice.getProperty() != null && invoice.getProperty().getOwner() != null) {
            notificationService.createNotification(
                    invoice.getProperty().getOwner(),
                    "A payment of " + saved.getAmount() + " has been made for your property: " +
                            invoice.getProperty().getAddress(),
                    ENotificationType.RENT_PAYMENT_SUCCESS
            );
        }

        return saved;
    }

    @Override
    public Payment getPaymentById(UUID id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID " + id));
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public void deletePayment(UUID id) {
        Payment payment = getPaymentById(id);
        paymentRepository.delete(payment);

        // Notify client about deletion
        if (payment.getClient() != null) {
            notificationService.createNotification(
                    payment.getClient(),
                    "Payment with reference " + payment.getReferenceNo() + " has been deleted.",
                    ENotificationType.RENT_PAYMENT_FAILED
            );
        }
    }

    @Override
    public List<Payment> getPaymentsByInvoice(UUID invoiceId) {
        return paymentRepository.findByInvoiceId(invoiceId);
    }

    @Override
    public List<Payment> getPaymentsByClient(UUID clientId) {
        return paymentRepository.findByClientId(clientId);
    }
}

