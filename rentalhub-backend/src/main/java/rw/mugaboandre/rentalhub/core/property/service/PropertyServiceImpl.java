package rw.mugaboandre.rentalhub.core.property.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.mugaboandre.rentalhub.core.client.model.Client;
import rw.mugaboandre.rentalhub.core.client.repository.IClientRepository;
import rw.mugaboandre.rentalhub.core.notification.service.INotificationService;
import rw.mugaboandre.rentalhub.core.owner.model.Owner;
import rw.mugaboandre.rentalhub.core.owner.repository.IOwnerRepository;
import rw.mugaboandre.rentalhub.core.property.dto.PropertyDto;
import rw.mugaboandre.rentalhub.core.property.model.Property;
import rw.mugaboandre.rentalhub.core.property.repository.IPropertyRepository;
import rw.mugaboandre.rentalhub.core.util.enums.notifications.ENotificationType;
import rw.mugaboandre.rentalhub.core.util.enums.property.EPropertyStatus;
import rw.mugaboandre.rentalhub.core.util.enums.property.EPropertyType;
import rw.mugaboandre.rentalhub.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PropertyServiceImpl implements IPropertyService {

    private final IPropertyRepository propertyRepository;
    private final INotificationService notificationService;
    private final IOwnerRepository ownerRepository;
    private final IClientRepository clientRepository;

    // -------------------- CREATE --------------------
    @Override
    public Property saveProperty(PropertyDto propertyDto) {
        // Find the managed Owner entity
        Owner owner = ownerRepository.findById(propertyDto.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found with id " + propertyDto.getOwnerId()));

        // Map DTO to entity
        Property property = new Property();
        property.setType(propertyDto.getType());
        property.setAddress(propertyDto.getAddress());
        property.setLatitude(propertyDto.getLatitude());
        property.setLongitude(propertyDto.getLongitude());
        property.setPrice(propertyDto.getPrice());
        property.setPropertyPreference(propertyDto.getPropertyPreference());
        property.setStatus(propertyDto.getStatus() != null ? propertyDto.getStatus() : EPropertyStatus.AVAILABLE);
        property.setMediaUrls(propertyDto.getMediaUrls());
        property.setOwner(owner);
        property.setActive(true);

        // ✅ New fields
        property.setCity(propertyDto.getCity());
        property.setDistrict(propertyDto.getDistrict());
        property.setAmenities(propertyDto.getAmenities());

        // Map client if provided
        if (propertyDto.getClientId() != null) {
            Client client = clientRepository.findById(propertyDto.getClientId())
                    .orElseThrow(() -> new RuntimeException("Client not found with id " + propertyDto.getClientId()));
            property.setClient(client);
        }

        // Validate latitude & longitude
        if (property.getLatitude() == null || property.getLongitude() == null) {
            throw new RuntimeException("Property latitude and longitude must be set");
        }

        Property saved = propertyRepository.save(property);

        // Notify owner
        notificationService.createNotification(
                saved.getOwner(),
                "Your property has been listed: " + saved.getAddress(),
                ENotificationType.PROPERTY_LISTED
        );

        return saved;
    }

    // -------------------- READ --------------------
    @Override
    public Optional<Property> getPropertyById(UUID id) {
        return propertyRepository.findById(id)
                .filter(Property::isActive);
    }

    @Override
    public List<Property> getAllProperties() {
        return propertyRepository.findByActiveTrue();
    }

    @Override
    public List<Property> getPropertiesByStatus(EPropertyStatus status) {
        return propertyRepository.findByStatusAndActiveTrue(status);
    }

    @Override
    public List<Property> getPropertiesByType(EPropertyType type) {
        return propertyRepository.findByTypeAndActiveTrue(type);
    }

    @Override
    public List<Property> getPropertiesByOwner(UUID ownerId) {
        return propertyRepository.findByOwnerIdAndActiveTrue(ownerId);
    }

    @Override
    public List<Property> getPropertiesByClient(UUID clientId) {
        return propertyRepository.findByClientIdAndActiveTrue(clientId);
    }

    // -------------------- UPDATE --------------------
    @Override
    public Property updateProperty(UUID id, PropertyDto propertyDto) {
        Property updated = propertyRepository.findById(id)
                .filter(Property::isActive)
                .map(existing -> {
                    existing.setType(propertyDto.getType());
                    existing.setAddress(propertyDto.getAddress());
                    existing.setPrice(propertyDto.getPrice());
                    existing.setStatus(propertyDto.getStatus());
                    existing.setMediaUrls(propertyDto.getMediaUrls());
                    existing.setLatitude(propertyDto.getLatitude());
                    existing.setLongitude(propertyDto.getLongitude());
                    existing.setPropertyPreference(propertyDto.getPropertyPreference());

                    // ✅ New fields
                    existing.setCity(propertyDto.getCity());
                    existing.setDistrict(propertyDto.getDistrict());
                    existing.setAmenities(propertyDto.getAmenities());

                    // Update owner if provided
                    if (propertyDto.getOwnerId() != null) {
                        Owner owner = ownerRepository.findById(propertyDto.getOwnerId())
                                .orElseThrow(() -> new RuntimeException("Owner not found with id " + propertyDto.getOwnerId()));
                        existing.setOwner(owner);
                    }

                    // Update client if provided
                    if (propertyDto.getClientId() != null) {
                        Client client = clientRepository.findById(propertyDto.getClientId())
                                .orElseThrow(() -> new RuntimeException("Client not found with id " + propertyDto.getClientId()));
                        existing.setClient(client);
                    }

                    return propertyRepository.save(existing);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id " + id));

        // Notify owner about update
        if (updated.getOwner() != null) {
            notificationService.createNotification(
                    updated.getOwner(),
                    "Your property has been updated: " + updated.getAddress(),
                    ENotificationType.PROPERTY_UPDATED
            );
        }

        return updated;
    }

    // -------------------- SOFT DELETE --------------------
    @Override
    public void deleteProperty(UUID id) {
        Property property = propertyRepository.findById(id)
                .filter(Property::isActive)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id " + id));

        property.setActive(false);
        propertyRepository.save(property);

        // Notify owner about soft delete
        if (property.getOwner() != null) {
            notificationService.createNotification(
                    property.getOwner(),
                    "Your property has been removed from listings: " + property.getAddress(),
                    ENotificationType.PROPERTY_UPDATED
            );
        }
    }
}
