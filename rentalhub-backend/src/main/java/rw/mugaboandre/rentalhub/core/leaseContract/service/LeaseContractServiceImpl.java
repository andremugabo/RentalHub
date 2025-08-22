package rw.mugaboandre.rentalhub.core.leaseContract.service;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import rw.mugaboandre.rentalhub.core.client.repository.IClientRepository;
import rw.mugaboandre.rentalhub.core.leaseContract.dto.LeaseContractDto;
import rw.mugaboandre.rentalhub.core.leaseContract.model.LeaseContract;
import rw.mugaboandre.rentalhub.core.property.repository.IPropertyRepository;
import rw.mugaboandre.rentalhub.core.leaseContract.repository.ILeaseContractRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LeaseContractServiceImpl implements ILeaseContractService {

    private final ILeaseContractRepository leaseRepository;
    private final IPropertyRepository propertyRepository;
    private final IClientRepository clientRepository;
    private final ModelMapper modelMapper;

    @Override
    public LeaseContract createLease(LeaseContractDto leaseDto) {
        LeaseContract lease = modelMapper.map(leaseDto, LeaseContract.class);

        lease.setProperty(propertyRepository.findById(leaseDto.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found")));
        lease.setClient(clientRepository.findById(leaseDto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found")));

        return leaseRepository.save(lease);
    }

    @Override
    public LeaseContract updateLease(UUID id, LeaseContractDto leaseDto) {
        LeaseContract lease = leaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lease not found"));

        lease.setStartDate(leaseDto.getStartDate());
        lease.setEndDate(leaseDto.getEndDate());
        lease.setTerms(leaseDto.getTerms());
        lease.setStatus(leaseDto.getStatus());

        // Optionally update property and client
        if (leaseDto.getPropertyId() != null) {
            lease.setProperty(propertyRepository.findById(leaseDto.getPropertyId())
                    .orElseThrow(() -> new RuntimeException("Property not found")));
        }
        if (leaseDto.getClientId() != null) {
            lease.setClient(clientRepository.findById(leaseDto.getClientId())
                    .orElseThrow(() -> new RuntimeException("Client not found")));
        }

        return leaseRepository.save(lease);
    }

    @Override
    public LeaseContract getLeaseById(UUID id) {
        return leaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lease not found"));
    }

    @Override
    public List<LeaseContract> getAllLeases() {
        return leaseRepository.findAll();
    }

    @Override
    public void deleteLease(UUID id) {
        LeaseContract lease = leaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lease not found"));
        leaseRepository.delete(lease);
    }
}
