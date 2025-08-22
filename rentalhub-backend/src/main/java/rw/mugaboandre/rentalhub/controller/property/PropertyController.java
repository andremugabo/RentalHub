package rw.mugaboandre.rentalhub.controller.property;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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

    //  Convert Entity -> DTO
    private PropertyDto toDto(Property property) {
        PropertyDto dto = modelMapper.map(property, PropertyDto.class);
        if (property.getOwner() != null) dto.setOwnerId(property.getOwner().getId());
        if (property.getClient() != null) dto.setClientId(property.getClient().getId());
        return dto;
    }

    //  Create property
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    @Operation(summary = "Create new property")
    public ResponseEntity<PropertyDto> createProperty(@RequestBody PropertyDto dto) {
        Property saved = propertyService.saveProperty(dto); // pass DTO directly
        return ResponseEntity.created(URI.create("/api/properties/" + saved.getId()))
                .body(toDto(saved));
    }

    //  Get property by ID
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get property by ID")
    public ResponseEntity<PropertyDto> getPropertyById(@PathVariable UUID id) {
        return propertyService.getPropertyById(id)
                .map(property -> ResponseEntity.ok(toDto(property)))
                .orElse(ResponseEntity.notFound().build());
    }

    //  Get all properties
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all properties")
    public ResponseEntity<List<PropertyDto>> getAllProperties() {
        List<PropertyDto> dtos = propertyService.getAllProperties()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    //  Get properties by status
    @GetMapping("/status/{status}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get properties by status")
    public ResponseEntity<List<PropertyDto>> getPropertiesByStatus(@PathVariable EPropertyStatus status) {
        List<PropertyDto> dtos = propertyService.getPropertiesByStatus(status)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    //  Get properties by type
    @GetMapping("/type/{type}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get properties by type")
    public ResponseEntity<List<PropertyDto>> getPropertiesByType(@PathVariable EPropertyType type) {
        List<PropertyDto> dtos = propertyService.getPropertiesByType(type)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    //  Update property
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    @Operation(summary = "Update property")
    public ResponseEntity<PropertyDto> updateProperty(@PathVariable UUID id, @RequestBody PropertyDto dto) {
        Property updated = propertyService.updateProperty(id, dto); // pass DTO directly
        return ResponseEntity.ok(toDto(updated));
    }

    //  Delete property
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    @Operation(summary = "Delete property")
    public ResponseEntity<Void> deleteProperty(@PathVariable UUID id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }


    // Get properties by owner
    @GetMapping("/owner/{ownerId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get properties by owner ID")
    public ResponseEntity<List<PropertyDto>> getPropertiesByOwner(@PathVariable UUID ownerId) {
        List<PropertyDto> dtos = propertyService.getPropertiesByOwner(ownerId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Get properties by client
    @GetMapping("/client/{clientId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get properties by client ID")
    public ResponseEntity<List<PropertyDto>> getPropertiesByClient(@PathVariable UUID clientId) {
        List<PropertyDto> dtos = propertyService.getPropertiesByClient(clientId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }




}
