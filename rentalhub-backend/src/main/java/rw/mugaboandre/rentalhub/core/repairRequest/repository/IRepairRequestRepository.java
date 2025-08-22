package rw.mugaboandre.rentalhub.core.repairRequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.mugaboandre.rentalhub.core.repairRequest.model.RepairRequest;

import java.util.List;
import java.util.UUID;

public interface IRepairRequestRepository extends JpaRepository<RepairRequest, UUID> {

    List<RepairRequest> findByPropertyId(UUID propertyId);

    List<RepairRequest> findByStatus(String status);
}
