package rw.mugaboandre.rentalhub.controller.repairRequest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rw.mugaboandre.rentalhub.core.repairRequest.dto.RepairRequestDto;
import rw.mugaboandre.rentalhub.core.repairRequest.model.RepairRequest;
import rw.mugaboandre.rentalhub.core.repairRequest.service.IRepairRequestService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/repairs")
@RequiredArgsConstructor
@Tag(name = "Repair Requests", description = "Endpoints for managing property repair requests")
public class RepairRequestController {

    private final IRepairRequestService repairService;

    @Operation(summary = "Create a new repair request")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER','CLIENT')")
    @PostMapping
    public ResponseEntity<RepairRequest> createRepair(@RequestBody RepairRequestDto dto) {
        RepairRequest repair = repairService.createRepairRequest(dto);
        return ResponseEntity.ok(repair);
    }

    @Operation(summary = "Update a repair request")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    @PutMapping("/{id}")
    public ResponseEntity<RepairRequest> updateRepair(@PathVariable UUID id, @RequestBody RepairRequestDto dto) {
        RepairRequest updated = repairService.updateRepairRequest(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Get repair request by ID")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER','CLIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<RepairRequest> getRepairById(@PathVariable UUID id) {
        RepairRequest repair = repairService.getRepairRequestById(id);
        return ResponseEntity.ok(repair);
    }

    @Operation(summary = "Get all repair requests")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    @GetMapping
    public ResponseEntity<List<RepairRequest>> getAllRepairs() {
        List<RepairRequest> repairs = repairService.getAllRepairRequests();
        return ResponseEntity.ok(repairs);
    }

    @Operation(summary = "Get all repair requests for a specific property")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER','CLIENT')")
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<RepairRequest>> getRepairsByProperty(@PathVariable UUID propertyId) {
        List<RepairRequest> repairs = repairService.getRepairRequestsByProperty(propertyId);
        return ResponseEntity.ok(repairs);
    }

    @Operation(summary = "Get all repair requests by status")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<RepairRequest>> getRepairsByStatus(@PathVariable String status) {
        List<RepairRequest> repairs = repairService.getRepairRequestsByStatus(status);
        return ResponseEntity.ok(repairs);
    }

    @Operation(summary = "Delete a repair request")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepair(@PathVariable UUID id) {
        repairService.deleteRepairRequest(id);
        return ResponseEntity.noContent().build();
    }
}

