package rw.mugaboandre.rentalhub.controller.payment;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rw.mugaboandre.rentalhub.core.payment.dto.PaymentDto;
import rw.mugaboandre.rentalhub.core.payment.model.Payment;
import rw.mugaboandre.rentalhub.core.payment.service.IPaymentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "Endpoints for managing payments")
public class PaymentController {

    private final IPaymentService paymentService;

    @Operation(summary = "Create a new payment")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER','CLIENT')")
    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody PaymentDto paymentDto) {
        Payment payment = paymentService.createPayment(paymentDto);
        return ResponseEntity.ok(payment);
    }

    @Operation(summary = "Get payment by ID")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER','CLIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable UUID id) {
        Payment payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    @Operation(summary = "Get all payments")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @Operation(summary = "Get all payments for a specific invoice")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER','CLIENT')")
    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<List<Payment>> getPaymentsByInvoice(@PathVariable UUID invoiceId) {
        List<Payment> payments = paymentService.getPaymentsByInvoice(invoiceId);
        return ResponseEntity.ok(payments);
    }

    @Operation(summary = "Get all payments made by a specific client")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER','CLIENT')")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Payment>> getPaymentsByClient(@PathVariable UUID clientId) {
        List<Payment> payments = paymentService.getPaymentsByClient(clientId);
        return ResponseEntity.ok(payments);
    }

    @Operation(summary = "Delete a payment")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable UUID id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
