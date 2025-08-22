package rw.mugaboandre.rentalhub.core.property.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.mugaboandre.rentalhub.core.notification.service.INotificationService;
import rw.mugaboandre.rentalhub.core.property.model.Property;
import rw.mugaboandre.rentalhub.core.property.repository.IPropertyRepository;
import rw.mugaboandre.rentalhub.core.util.enums.notifications.ENotificationType;
import rw.mugaboandre.rentalhub.core.util.enums.property.EPropertyStatus;
import rw.mugaboandre.rentalhub.core.util.enums.property.EPropertyType;
import rw.mugaboandre.rentalhub.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PropertyServiceImpl implements IPropertyService {

    private final IPropertyRepository propertyRepository;
    private final INotificationService notificationService;

    // -------------------- CREATE --------------------
    @Override
    public Property saveProperty(Property property) {
        property.setActive(true);
        Property saved = propertyRepository.save(property);

        // Notify owner
        if (saved.getOwner() != null) {
            notificationService.createNotification(
                    saved.getOwner(),
                    "Your property has been listed: " + saved.getAddress(),
                    ENotificationType.PROPERTY_LISTED
            );
        }

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
    public Property updateProperty(UUID id, Property propertyDetails) {
        Property updated = propertyRepository.findById(id)
                .filter(Property::isActive)
                .map(existing -> {
                    existing.setType(propertyDetails.getType());
                    existing.setAddress(propertyDetails.getAddress());
                    existing.setPrice(propertyDetails.getPrice());
                    existing.setStatus(propertyDetails.getStatus());
                    existing.setMediaUrls(propertyDetails.getMediaUrls());
                    existing.setOwner(propertyDetails.getOwner());
                    existing.setClient(propertyDetails.getClient());
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

        // Notify client if property is rented
        if (updated.getClient() != null && updated.getStatus() == EPropertyStatus.RENTED) {
            notificationService.createNotification(
                    updated.getClient(),
                    "The property you are renting has been updated: " + updated.getAddress(),
                    ENotificationType.PROPERTY_RENTED
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
