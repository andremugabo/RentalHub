package rw.mugaboandre.rentalhub.controller.property;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rw.mugaboandre.rentalhub.core.client.model.Client;
import rw.mugaboandre.rentalhub.core.owner.model.Owner;
import rw.mugaboandre.rentalhub.core.property.dto.PropertyDto;
import rw.mugaboandre.rentalhub.core.property.model.Property;
import rw.mugaboandre.rentalhub.core.property.service.IPropertyService;
import rw.mugaboandre.rentalhub.core.util.enums.property.EPropertyStatus;
import rw.mugaboandre.rentalhub.core.util.enums.property.EPropertyType;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
@Tag(name = "Property", description = "Manage rental properties")
@SecurityRequirement(name = "bearerAuth")
public class PropertyController {

    private final IPropertyService propertyService;
    private final ModelMapper modelMapper;

    // ✅ Convert Entity -> DTO
    private PropertyDto toDto(Property property) {
        PropertyDto dto = modelMapper.map(property, PropertyDto.class);
        dto.setOwnerId(property.getOwner() != null ? property.getOwner().getId() : null);
        dto.setClientId(property.getClient() != null ? property.getClient().getId() : null);
        return dto;
    }

    // ✅ Convert DTO -> Entity
    private Property toEntity(PropertyDto dto) {
        Property property = modelMapper.map(dto, Property.class);

        if (dto.getOwnerId() != null) {
            Owner owner = new Owner();
            owner.setId(dto.getOwnerId());
            property.setOwner(owner);
        }

        if (dto.getClientId() != null) {
            Client client = new Client();
            client.setId(dto.getClientId());
            property.setClient(client);
        }

        return property;
    }

    // ✅ Create property
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    @Operation(summary = "Create new property")
    public ResponseEntity<PropertyDto> createProperty(@RequestBody PropertyDto dto) {
        Property saved = propertyService.saveProperty(toEntity(dto));
        return ResponseEntity.created(URI.create("/api/properties/" + saved.getId())).body(toDto(saved));
    }

    // ✅ Get by ID
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get property by ID")
    public ResponseEntity<PropertyDto> getPropertyById(@PathVariable UUID id) {
        return propertyService.getPropertyById(id)
                .map(property -> ResponseEntity.ok(toDto(property)))
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Get all
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all properties")
    public ResponseEntity<List<PropertyDto>> getAllProperties() {
        return ResponseEntity.ok(
                propertyService.getAllProperties().stream().map(this::toDto).collect(Collectors.toList())
        );
    }

    // ✅ Filter by status
    @GetMapping("/status/{status}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get properties by status")
    public ResponseEntity<List<PropertyDto>> getPropertiesByStatus(@PathVariable EPropertyStatus status) {
        return ResponseEntity.ok(
                propertyService.getPropertiesByStatus(status).stream().map(this::toDto).collect(Collectors.toList())
        );
    }

    // ✅ Filter by type
    @GetMapping("/type/{type}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get properties by type")
    public ResponseEntity<List<PropertyDto>> getPropertiesByType(@PathVariable EPropertyType type) {
        return ResponseEntity.ok(
                propertyService.getPropertiesByType(type).stream().map(this::toDto).collect(Collectors.toList())
        );
    }

    // ✅ Update property
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    @Operation(summary = "Update property")
    public ResponseEntity<PropertyDto> updateProperty(@PathVariable UUID id, @RequestBody PropertyDto dto) {
        Property updated = propertyService.updateProperty(id, toEntity(dto));
        return ResponseEntity.ok(toDto(updated));
    }

    // ✅ Delete property
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    @Operation(summary = "Delete property")
    public ResponseEntity<Void> deleteProperty(@PathVariable UUID id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }
}
