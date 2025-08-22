package rw.mugaboandre.rentalhub.core.property.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.mugaboandre.rentalhub.core.property.model.Property;
import rw.mugaboandre.rentalhub.core.property.repository.IPropertyRepository;
import rw.mugaboandre.rentalhub.core.util.enums.property.EPropertyStatus;
import rw.mugaboandre.rentalhub.core.util.enums.property.EPropertyType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PropertyServiceImpl implements IPropertyService {

    private final IPropertyRepository propertyRepository;

    @Override
    public Property saveProperty(Property property) {
        return propertyRepository.save(property);
    }

    @Override
    public Optional<Property> getPropertyById(UUID id) {
        return propertyRepository.findById(id);
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

    @Override
    public Property updateProperty(UUID id, Property propertyDetails) {
        return propertyRepository.findById(id)
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
                .orElseThrow(() -> new RuntimeException("Property not found with id " + id));
    }

    @Override
    public void deleteProperty(UUID id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found with id " + id));

        property.setActive(false);
        propertyRepository.save(property);
    }

}
