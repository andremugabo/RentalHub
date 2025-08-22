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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/leases")
@RequiredArgsConstructor
@Tag(name = "Lease Contract", description = "Endpoints for managing lease contracts")
public class LeaseContractController {

    private final ILeaseContractService leaseService;

    @Operation(summary = "Create a new lease")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    @PostMapping
    public ResponseEntity<LeaseContract> createLease(@RequestBody LeaseContractDto leaseDto) {
        LeaseContract lease = leaseService.createLease(leaseDto);
        return ResponseEntity.ok(lease);
    }

    @Operation(summary = "Update a lease")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    @PutMapping("/{id}")
    public ResponseEntity<LeaseContract> updateLease(@PathVariable UUID id, @RequestBody LeaseContractDto leaseDto) {
        LeaseContract updated = leaseService.updateLease(id, leaseDto);
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
        leaseService.deleteLease(id);
        return ResponseEntity.noContent().build();
    }
}
