package rw.mugaboandre.rentalhub.core.property.service;

import rw.mugaboandre.rentalhub.core.property.dto.PropertyDto;
import rw.mugaboandre.rentalhub.core.property.model.Property;
import rw.mugaboandre.rentalhub.core.util.enums.property.EPropertyStatus;
import rw.mugaboandre.rentalhub.core.util.enums.property.EPropertyType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPropertyService {

    Property saveProperty(PropertyDto propertyDto);

    Optional<Property> getPropertyById(UUID id);

    List<Property> getAllProperties();

    List<Property> getPropertiesByStatus(EPropertyStatus status);

    List<Property> getPropertiesByType(EPropertyType type);

    List<Property> getPropertiesByOwner(UUID ownerId);

    List<Property> getPropertiesByClient(UUID clientId);

    Property updateProperty(UUID id, PropertyDto propertyDetails);

    void deleteProperty(UUID id);
}
