package rw.mugaboandre.rentalhub.controller.leaseContract;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rw.mugaboandre.rentalhub.core.leaseContract.dto.LeaseContractDto;
import rw.mugaboandre.rentalhub.core.leaseContract.model.LeaseContract;
import rw.mugaboandre.rentalhub.core.leaseContract.service.ILeaseContractService;
import rw.mugaboandre.rentalhub.core.notification.service.INotificationService;
import rw.mugaboandre.rentalhub.core.util.enums.notifications.ENotificationType;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/leases")
@RequiredArgsConstructor
@Tag(name = "Lease Contract", description = "Endpoints for managing lease contracts")
public class LeaseContractController {

    private final ILeaseContractService leaseService;
    private final INotificationService notificationService;

    @Operation(summary = "Create a new lease")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    @PostMapping
    public ResponseEntity<LeaseContract> createLease(@RequestBody LeaseContractDto leaseDto) {
        LeaseContract lease = leaseService.createLease(leaseDto);

        // Notify owner and client
        if (lease.getProperty().getOwner() != null) {
            notificationService.createNotification(
                    lease.getProperty().getOwner(),
                    "A new lease has been created for your property: " + lease.getProperty().getAddress(),
                    ENotificationType.LEASE_SIGNED
            );
        }

        if (lease.getClient() != null) {
            notificationService.createNotification(
                    lease.getClient(),
                    "You have successfully signed a lease for property: " + lease.getProperty().getAddress(),
                    ENotificationType.LEASE_SIGNED
            );
        }

        return ResponseEntity.ok(lease);
    }

    @Operation(summary = "Update a lease")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    @PutMapping("/{id}")
    public ResponseEntity<LeaseContract> updateLease(@PathVariable UUID id, @RequestBody LeaseContractDto leaseDto) {
        LeaseContract updated = leaseService.updateLease(id, leaseDto);

        // Notify owner and client about update
        if (updated.getProperty().getOwner() != null) {
            notificationService.createNotification(
                    updated.getProperty().getOwner(),
                    "Lease has been updated for your property: " + updated.getProperty().getAddress(),
                    ENotificationType.LEASE_RENEWAL
            );
        }

        if (updated.getClient() != null) {
            notificationService.createNotification(
                    updated.getClient(),
                    "Your lease has been updated for property: " + updated.getProperty().getAddress(),
                    ENotificationType.LEASE_RENEWAL
            );
        }

        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Get lease by ID")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER','CLIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<LeaseContract> getLease(@PathVariable UUID id) {
        LeaseContract lease = leaseService.getLeaseById(id);
        return ResponseEntity.ok(lease);
    }

    @Operation(summary = "Get all leases")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    @GetMapping
    public ResponseEntity<List<LeaseContract>> getAllLeases() {
        List<LeaseContract> leases = leaseService.getAllLeases();
        return ResponseEntity.ok(leases);
    }

    @Operation(summary = "Delete a lease")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLease(@PathVariable UUID id) {
        LeaseContract lease = leaseService.getLeaseById(id);

        // Notify owner and client before deletion
        if (lease.getProperty().getOwner() != null) {
            notificationService.createNotification(
                    lease.getProperty().getOwner(),
                    "Lease has been terminated for your property: " + lease.getProperty().getAddress(),
                    ENotificationType.LEASE_TERMINATION
            );
        }

        if (lease.getClient() != null) {
            notificationService.createNotification(
                    lease.getClient(),
                    "Your lease has been terminated for property: " + lease.getProperty().getAddress(),
                    ENotificationType.LEASE_TERMINATION
            );
        }

        leaseService.deleteLease(id);
        return ResponseEntity.noContent().build();
    }
}
