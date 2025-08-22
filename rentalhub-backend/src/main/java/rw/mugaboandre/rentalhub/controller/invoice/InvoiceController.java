package rw.mugaboandre.rentalhub.controller.invoice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rw.mugaboandre.rentalhub.core.invoice.dto.InvoiceDto;
import rw.mugaboandre.rentalhub.core.invoice.dto.InvoiceResponseDto;
import rw.mugaboandre.rentalhub.core.invoice.model.Invoice;
import rw.mugaboandre.rentalhub.core.invoice.service.InvoiceService;
import rw.mugaboandre.rentalhub.core.notification.service.INotificationService;
import rw.mugaboandre.rentalhub.core.util.enums.notifications.ENotificationType;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
@Tag(name = "Invoices", description = "Manage rent invoices")
@SecurityRequirement(name = "bearerAuth") // Swagger: require Bearer JWT
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final INotificationService notificationService;

    // ---------- CREATE ----------
    @Operation(summary = "Create a new invoice")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<InvoiceResponseDto> createInvoice(@Valid @RequestBody InvoiceDto dto) {
        InvoiceResponseDto response = invoiceService.createInvoice(dto);

        // Load the entity to reach the recipient (client) for notification
        Invoice invoice = invoiceService.getEntity(response.getId());
        notificationService.createNotification(
                invoice.getClient(),
                "A new invoice was created for amount: " + response.getAmount()
                        + ", due on " + response.getDueDate() + ".",
                ENotificationType.RENT_DUE_REMINDER
        );

        return ResponseEntity.ok(response);
    }

    // ---------- READ ----------
    @Operation(summary = "Get invoice by ID")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponseDto> getInvoiceById(@PathVariable UUID id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @Operation(summary = "List all invoices")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<InvoiceResponseDto>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    // ---------- UPDATE ----------
    @Operation(summary = "Update an invoice by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<InvoiceResponseDto> updateInvoice(
            @PathVariable UUID id,
            @Valid @RequestBody InvoiceDto dto
    ) {
        InvoiceResponseDto response = invoiceService.updateInvoice(id, dto);

        Invoice invoice = invoiceService.getEntity(response.getId());
        notificationService.createNotification(
                invoice.getClient(),
                "Your invoice was updated. New amount: " + response.getAmount()
                        + ", due on " + response.getDueDate() + ".",
                ENotificationType.ANNOUNCEMENT
        );

        return ResponseEntity.ok(response);
    }

    // ---------- DELETE ----------
    @Operation(summary = "Delete an invoice by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable UUID id) {
        // Notify before delete so we can still read client
        Invoice invoice = invoiceService.getEntity(id);
        notificationService.createNotification(
                invoice.getClient(),
                "Your invoice with ID " + id + " was cancelled.",
                ENotificationType.ANNOUNCEMENT
        );

        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
}
