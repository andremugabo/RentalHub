package rw.mugaboandre.rentalhub.core.PropertyPreference.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import rw.mugaboandre.rentalhub.core.PropertyPreference.dto.PropertyPreferenceDto;
import rw.mugaboandre.rentalhub.core.PropertyPreference.model.PropertyPreference;
import rw.mugaboandre.rentalhub.core.PropertyPreference.repository.IPropertyPreferenceRepository;
import rw.mugaboandre.rentalhub.core.client.repository.IClientRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyPreferenceServiceImpl implements IPropertyPreferenceService {

    private final IPropertyPreferenceRepository preferenceRepository;
    private final IClientRepository clientRepository;
    private final ModelMapper modelMapper;

    @Override
    public Optional<PropertyPreferenceDto> getPreferenceById(UUID id) {
        return preferenceRepository.findById(id)
                .map(pref -> modelMapper.map(pref, PropertyPreferenceDto.class));
    }

    @Override
    public List<PropertyPreferenceDto> getPreferencesByClient(UUID clientId) {
        return preferenceRepository.findByClientId(clientId)
                .stream()
                .map(pref -> modelMapper.map(pref, PropertyPreferenceDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PropertyPreferenceDto createPreference(PropertyPreferenceDto dto) {
        PropertyPreference preference = modelMapper.map(dto, PropertyPreference.class);

        clientRepository.findById(dto.getClientId())
                .ifPresent(preference::setClient);

        PropertyPreference saved = preferenceRepository.save(preference);
        return modelMapper.map(saved, PropertyPreferenceDto.class);
    }

    @Override
    public PropertyPreferenceDto updatePreference(UUID id, PropertyPreferenceDto dto) {
        PropertyPreference updated = preferenceRepository.findById(id)
                .map(existing -> {
                    existing.setPropertyPreference(dto.getPropertyPreference());
                    existing.setMinPrice(dto.getMinPrice());
                    existing.setMaxPrice(dto.getMaxPrice());
                    existing.setCity(dto.getCity());
                    existing.setDistrict(dto.getDistrict());
                    existing.setAmenities(dto.getAmenities());
                    // client should not change
                    return preferenceRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Preference with ID " + id + " not found"));

        return modelMapper.map(updated, PropertyPreferenceDto.class);
    }

    @Override
    public void deletePreference(UUID id) {
        PropertyPreference preference = preferenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Preference with ID " + id + " not found"));
        preferenceRepository.delete(preference);
    }
}
