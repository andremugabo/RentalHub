package rw.mugaboandre.rentalhub.core.leaseContract.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.mugaboandre.rentalhub.core.leaseContract.model.LeaseContract;

import java.util.UUID;

public interface ILeaseContractRepository extends JpaRepository<LeaseContract, UUID> {
}
