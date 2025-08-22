package rw.mugaboandre.rentalhub.core.client.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rw.mugaboandre.rentalhub.core.PropertyPreference.dto.PropertyPreferenceDto;
import rw.mugaboandre.rentalhub.core.PropertyPreference.model.PropertyPreference;
import rw.mugaboandre.rentalhub.core.client.model.Client;
import rw.mugaboandre.rentalhub.core.client.repository.IClientRepository;
import rw.mugaboandre.rentalhub.core.property.model.Property;
import rw.mugaboandre.rentalhub.core.property.repository.IPropertyRepository;
import rw.mugaboandre.rentalhub.core.util.utilClass.MailService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;
    private final MailService mailService;
    private final IPropertyRepository propertyRepository;

    @Override
    public Optional<Client> getClientById(UUID id) {
        return clientRepository.findById(id);
    }

    @Override
    public Client updateClient(UUID id, Client updatedClient) {
        return clientRepository.findById(id).map(existingClient -> {
            // Update Person fields
            existingClient.setFirstName(updatedClient.getFirstName());
            existingClient.setLastName(updatedClient.getLastName());
            existingClient.setEmail(updatedClient.getEmail());
            existingClient.setUsername(updatedClient.getUsername());
            existingClient.setPhone(updatedClient.getPhone());
            existingClient.setPassword(updatedClient.getPassword());
            existingClient.setRole(updatedClient.getRole());
            existingClient.setPermissions(updatedClient.getPermissions());
            existingClient.setContactPref(updatedClient.getContactPref());
            existingClient.setProfilePic(updatedClient.getProfilePic());

            // Update Client-specific fields
            existingClient.setPreferences(updatedClient.getPreferences());

            return clientRepository.save(existingClient);
        }).orElseThrow(() -> new RuntimeException("Client with ID " + id + " not found"));
    }

    @Override
    public void deleteClient(UUID id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client with ID " + id + " not found"));
        client.setActive(false);
        clientRepository.save(client);
        mailService.sendUnsubscribeEmail(client.getEmail(), client.getFirstName());
    }

    @Override
    public void restoreClient(UUID id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client with ID " + id + " not found"));
        client.setActive(true);
        clientRepository.save(client);
    }

    @Override
    public List<Client> findClientsByPreference(PropertyPreferenceDto preference) {
        // Use repository query instead of stream filtering
        return clientRepository.findByPreferences(preference);
    }

    @Override
    public Client updatePreferences(UUID clientId, List<PropertyPreferenceDto> newPreferences) {
        return clientRepository.findById(clientId).map(client -> {

            // Convert DTOs to entities
            List<PropertyPreference> preferenceEntities = newPreferences.stream()
                    .map(dto -> {
                        PropertyPreference preference = new PropertyPreference();
                        preference.setId(dto.getId());
                        preference.setPropertyPreference(dto.getPropertyPreference());
                        preference.setMinPrice(dto.getMinPrice());
                        preference.setMaxPrice(dto.getMaxPrice());
                        preference.setCity(dto.getCity());
                        preference.setDistrict(dto.getDistrict());
                        preference.setAmenities(dto.getAmenities());
                        preference.setClient(client);
                        return preference;
                    })
                    .toList();

            client.setPreferences(preferenceEntities); // assuming entity field is List<PropertyPreference>
            return clientRepository.save(client);

        }).orElseThrow(() -> new RuntimeException("Client with ID " + clientId + " not found"));
    }

    @Override
    public List<Property> suggestPropertiesForClient(List<PropertyPreference> preferences) {
        List<Property> suggested = new ArrayList<>();

        for (PropertyPreference pref : preferences) {
            List<Property> matches = propertyRepository.findByTypeAndStatusAndPriceBetweenAndCityAndDistrict(
                    pref.getPropertyPreference().name(),
                    "AVAILABLE",
                    pref.getMinPrice(),
                    pref.getMaxPrice(),
                    pref.getCity(),
                    pref.getDistrict()
            );
            // Optional: filter by amenities
            matches.removeIf(property -> !new HashSet<>(property.getAmenities()).containsAll(pref.getAmenities()));

            suggested.addAll(matches);
        }

        return suggested;
    }


    // Optional: Add a create method
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }
}
