package rw.mugaboandre.rentalhub.core.leaseContract.service;


import rw.mugaboandre.rentalhub.core.leaseContract.dto.LeaseContractDto;
import rw.mugaboandre.rentalhub.core.leaseContract.model.LeaseContract;

import java.util.List;
import java.util.UUID;

public interface ILeaseContractService {

    LeaseContract createLease(LeaseContractDto leaseDto);

    LeaseContract updateLease(UUID id, LeaseContractDto leaseDto);

    LeaseContract getLeaseById(UUID id);

    List<LeaseContract> getAllLeases();

    void deleteLease(UUID id);
}
