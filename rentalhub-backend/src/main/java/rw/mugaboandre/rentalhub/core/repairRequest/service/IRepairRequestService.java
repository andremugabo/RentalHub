package rw.mugaboandre.rentalhub.core.repairRequest.service;


import rw.mugaboandre.rentalhub.core.repairRequest.dto.RepairRequestDto;
import rw.mugaboandre.rentalhub.core.repairRequest.model.RepairRequest;

import java.util.List;
import java.util.UUID;

public interface IRepairRequestService {

    RepairRequest createRepairRequest(RepairRequestDto dto);

    RepairRequest updateRepairRequest(UUID id, RepairRequestDto dto);

    RepairRequest getRepairRequestById(UUID id);

    List<RepairRequest> getAllRepairRequests();

    List<RepairRequest> getRepairRequestsByProperty(UUID propertyId);

    List<RepairRequest> getRepairRequestsByStatus(String status);

    void deleteRepairRequest(UUID id);
}
