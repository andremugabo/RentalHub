package rw.mugaboandre.rentalhub.core.client.service;

import rw.mugaboandre.rentalhub.core.PropertyPreference.dto.PropertyPreferenceDto;
import rw.mugaboandre.rentalhub.core.PropertyPreference.model.PropertyPreference;
import rw.mugaboandre.rentalhub.core.client.model.Client;
import rw.mugaboandre.rentalhub.core.property.model.Property;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IClientService {

    Optional<Client> getClientById(UUID id);

    Client updateClient(UUID id, Client client);

    void deleteClient(UUID id);

    void restoreClient(UUID id);

    // Find clients who have a specific property preference
    List<Client> findClientsByPreference(PropertyPreferenceDto preference);

    // Update a client's preferences (replace existing with new list)
    Client updatePreferences(UUID clientId, List<PropertyPreferenceDto> newPreferences);
    List<Property> suggestPropertiesForClient(List<PropertyPreference> preferences);
}
