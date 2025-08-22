package rw.mugaboandre.rentalhub.controller.client;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import rw.mugaboandre.rentalhub.core.PropertyPreference.dto.PropertyPreferenceDto;
import rw.mugaboandre.rentalhub.core.PropertyPreference.model.PropertyPreference;
import rw.mugaboandre.rentalhub.core.PropertyPreference.service.IPropertyPreferenceService;
import rw.mugaboandre.rentalhub.core.client.model.Client;
import rw.mugaboandre.rentalhub.core.client.service.IClientService;
import rw.mugaboandre.rentalhub.core.property.model.Property;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "Client", description = "Endpoints for managing clients")
public class ClientController {

    private final IClientService clientService;
    private final IPropertyPreferenceService preferenceService;

    // ----------------- READ -----------------
    @Operation(summary = "Get client by ID")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable UUID id) {
        return clientService.getClientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Find clients by preference ID")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<List<Client>> findClientsByPreference(@RequestParam UUID preferenceId) {
        // Fetch preference DTO
        PropertyPreferenceDto preferenceDto = preferenceService.getPreferenceById(preferenceId)
                .orElseThrow(() -> new RuntimeException("Preference with ID " + preferenceId + " not found"));

        // Find clients based on preference DTO
        List<Client> clients = clientService.findClientsByPreference(preferenceDto);

        return ResponseEntity.ok(clients);
    }

    // ----------------- UPDATE -----------------
    @Operation(summary = "Update client details")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable UUID id, @RequestBody Client client) {
        Client updatedClient = clientService.updateClient(id, client);
        return ResponseEntity.ok(updatedClient);
    }

    @Operation(summary = "Update client preferences")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT')")
    @PatchMapping("/{id}/preferences")
    public ResponseEntity<Client> updatePreferences(
            @PathVariable UUID id,
            @RequestBody List<UUID> preferenceIds) {

        // Convert preference IDs to DTOs
        List<PropertyPreferenceDto> newPreferences = preferenceIds.stream()
                .map(preferenceService::getPreferenceById)
                .map(opt -> opt.orElseThrow(() -> new RuntimeException("Preference not found")))
                .toList();

        // Update client preferences via service
        Client updatedClient = clientService.updatePreferences(id, newPreferences);

        return ResponseEntity.ok(updatedClient);
    }


    // ----------------- SOFT DELETE / RESTORE -----------------
    @Operation(summary = "Soft delete (unsubscribe) a client")
    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> unsubscribeClient(@PathVariable UUID id, Authentication authentication) {
        String loggedInUsername = authentication.getName();

        Client client = clientService.getClientById(id)
                .orElseThrow(() -> new RuntimeException("Client with ID " + id + " not found"));

        if (!client.getUsername().equals(loggedInUsername)) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Restore a previously unsubscribed client")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/restore")
    public ResponseEntity<Void> restoreClient(@PathVariable UUID id) {
        clientService.restoreClient(id);
        return ResponseEntity.noContent().build();
    }

    // Basic AI like suggestion for client to properties based on client preference

    // ----------------- SUGGEST PROPERTIES -----------------
    @Operation(summary = "Get suggested properties for a client based on their preferences")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT')")
    @GetMapping("/{id}/suggested-properties")
    public ResponseEntity<List<Property>> getSuggestedProperties(@PathVariable UUID id) {
        // Fetch client
        Client client = clientService.getClientById(id)
                .orElseThrow(() -> new RuntimeException("Client with ID " + id + " not found"));

        // Get client's preferences
        List<PropertyPreference> preferences = client.getPreferences();

        // Suggest properties using service method
        List<Property> suggestedProperties = clientService.suggestPropertiesForClient(preferences);

        return ResponseEntity.ok(suggestedProperties);
    }


}
