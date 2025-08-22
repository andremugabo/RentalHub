package rw.mugaboandre.rentalhub.core.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.mugaboandre.rentalhub.core.PropertyPreference.dto.PropertyPreferenceDto;
import rw.mugaboandre.rentalhub.core.client.model.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IClientRepository extends JpaRepository<Client, UUID> {

    List<Client> findByPreferences(PropertyPreferenceDto preference);

    List<Client> findByLeaseHistoryIsNotEmpty();

    Optional<Client> findByIdAndPreferencesIsNotEmpty(UUID id);
}
