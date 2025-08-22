package rw.mugaboandre.rentalhub.core.PropertyPreference.service;

import rw.mugaboandre.rentalhub.core.PropertyPreference.dto.PropertyPreferenceDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPropertyPreferenceService {

    Optional<PropertyPreferenceDto> getPreferenceById(UUID id);

    List<PropertyPreferenceDto> getPreferencesByClient(UUID clientId);

    PropertyPreferenceDto createPreference(PropertyPreferenceDto preferenceDto);

    PropertyPreferenceDto updatePreference(UUID id, PropertyPreferenceDto updatedPreferenceDto);

    void deletePreference(UUID id);
}
