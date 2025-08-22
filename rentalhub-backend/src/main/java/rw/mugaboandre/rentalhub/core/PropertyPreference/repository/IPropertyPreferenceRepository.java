package rw.mugaboandre.rentalhub.core.PropertyPreference.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.mugaboandre.rentalhub.core.PropertyPreference.model.PropertyPreference;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPropertyPreferenceRepository extends JpaRepository<PropertyPreference, UUID> {

    // Find all preferences for a specific client
    List<PropertyPreference> findByClientId(UUID clientId);

    // Optionally: find by preference type for a client
    List<PropertyPreference> findByClientIdAndPropertyPreference(UUID clientId, rw.mugaboandre.rentalhub.core.util.enums.property.EPropertyPreference type);

    // Find preference by ID and client (to secure updates)
    Optional<PropertyPreference> findByIdAndClientId(UUID id, UUID clientId);
}
