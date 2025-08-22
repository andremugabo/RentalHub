package rw.mugaboandre.rentalhub.controller.propertyPreference;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rw.mugaboandre.rentalhub.core.PropertyPreference.dto.PropertyPreferenceDto;
import rw.mugaboandre.rentalhub.core.PropertyPreference.service.IPropertyPreferenceService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/preferences")
@RequiredArgsConstructor
@Tag(name = "Property Preference", description = "Endpoints for managing property preferences for clients")
public class PropertyPreferenceController {

    private final IPropertyPreferenceService preferenceService;

    // ----------------- READ -----------------
    @Operation(summary = "Get preference by ID")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<PropertyPreferenceDto> getPreferenceById(@PathVariable UUID id) {
        return preferenceService.getPreferenceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all preferences for a client")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT')")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<PropertyPreferenceDto>> getPreferencesByClient(@PathVariable UUID clientId) {
        List<PropertyPreferenceDto> preferences = preferenceService.getPreferencesByClient(clientId);
        return ResponseEntity.ok(preferences);
    }

    // ----------------- CREATE -----------------
    @Operation(summary = "Create a new preference for a client")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT')")
    @PostMapping
    public ResponseEntity<PropertyPreferenceDto> createPreference(@RequestBody PropertyPreferenceDto preferenceDto) {
        PropertyPreferenceDto created = preferenceService.createPreference(preferenceDto);
        return ResponseEntity.ok(created);
    }

    // ----------------- UPDATE -----------------
    @Operation(summary = "Update an existing preference")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT')")
    @PutMapping("/{id}")
    public ResponseEntity<PropertyPreferenceDto> updatePreference(
            @PathVariable UUID id,
            @RequestBody PropertyPreferenceDto updatedPreferenceDto) {

        PropertyPreferenceDto updated = preferenceService.updatePreference(id, updatedPreferenceDto);
        return ResponseEntity.ok(updated);
    }

    // ----------------- DELETE -----------------
    @Operation(summary = "Delete a preference")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePreference(@PathVariable UUID id) {
        preferenceService.deletePreference(id);
        return ResponseEntity.noContent().build();
    }
}
