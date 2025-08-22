package rw.mugaboandre.rentalhub.core.property.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.mugaboandre.rentalhub.core.property.model.Property;
import rw.mugaboandre.rentalhub.core.util.enums.property.EPropertyStatus;
import rw.mugaboandre.rentalhub.core.util.enums.property.EPropertyType;

import java.util.List;
import java.util.UUID;

@Repository
public interface IPropertyRepository extends JpaRepository<Property, UUID> {

    List<Property> findByStatus(EPropertyStatus status);

    List<Property> findByType(EPropertyType type);

    List<Property> findByOwnerId(UUID ownerId);

    List<Property> findByClientId(UUID clientId);

    List<Property> findByActiveTrue();

    List<Property> findByStatusAndActiveTrue(EPropertyStatus status);

    List<Property> findByTypeAndActiveTrue(EPropertyType type);

    List<Property> findByOwnerIdAndActiveTrue(UUID ownerId);

    List<Property> findByClientIdAndActiveTrue(UUID clientId);

}
