package rw.mugaboandre.rentalhub.core.repairRequest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.mugaboandre.rentalhub.core.notification.service.INotificationService;
import rw.mugaboandre.rentalhub.core.owner.model.Owner;
import rw.mugaboandre.rentalhub.core.property.model.Property;
import rw.mugaboandre.rentalhub.core.property.repository.IPropertyRepository;
import rw.mugaboandre.rentalhub.core.repairRequest.dto.RepairRequestDto;
import rw.mugaboandre.rentalhub.core.repairRequest.model.RepairRequest;
import rw.mugaboandre.rentalhub.core.repairRequest.repository.IRepairRequestRepository;
import rw.mugaboandre.rentalhub.core.util.enums.notifications.ENotificationType;
import rw.mugaboandre.rentalhub.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RepairRequestServiceImpl implements IRepairRequestService {

    private final IRepairRequestRepository repairRepository;
    private final INotificationService notificationService;
    private final IPropertyRepository propertyRepository;

    @Override
    public RepairRequest createRepairRequest(RepairRequestDto dto) {
        // Fetch the property
        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id " + dto.getPropertyId()));

        // Create the RepairRequest entity
        RepairRequest repair = new RepairRequest();
        repair.setDescription(dto.getDescription());
        repair.setEstimatedCost(dto.getEstimatedCost());
        repair.setAssignedTeam(dto.getAssignedTeam());
        repair.setProperty(property); // ✅ now this works
        repair.setStatus(dto.getStatus() != null ? dto.getStatus() : repair.getStatus());

        // Save repair request
        RepairRequest saved = repairRepository.save(repair);

        // Notify property owner
        Owner owner = saved.getProperty().getOwner();
        if (owner != null) {
            notificationService.createNotification(
                    owner,
                    "New repair request created for your property: " + saved.getProperty().getAddress(),
                    ENotificationType.TENANT_REQUEST
            );
        }

        // Notify client associated with the property
        if (saved.getProperty().getClient() != null) {
            notificationService.createNotification(
                    saved.getProperty().getClient(),
                    "Your repair request has been submitted for property: " + saved.getProperty().getAddress(),
                    ENotificationType.MESSAGE
            );
        }

        return saved;
    }


    @Override
    public RepairRequest updateRepairRequest(UUID id, RepairRequestDto dto) {
        RepairRequest repair = repairRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RepairRequest not found with id " + id));

        repair.setDescription(dto.getDescription());
        repair.setEstimatedCost(dto.getEstimatedCost());
        repair.setActualCost(dto.getActualCost());
        repair.setAssignedTeam(dto.getAssignedTeam());
        repair.setStatus(dto.getStatus() != null ? dto.getStatus() : repair.getStatus());

        RepairRequest updated = repairRepository.save(repair);

        // Notify property owner about update
        Owner owner = updated.getProperty().getOwner();
        if (owner != null) {
            notificationService.createNotification(
                    owner,
                    "Repair request updated for your property: " + updated.getProperty().getAddress() +
                            " | Status: " + updated.getStatus(),
                    ENotificationType.LANDLORD_RESPONSE
            );
        }

        // Notify client about update
        if (updated.getProperty().getClient() != null) {
            notificationService.createNotification(
                    updated.getProperty().getClient(),
                    "Your repair request has been updated for property: " + updated.getProperty().getAddress() +
                            " | Status: " + updated.getStatus(),
                    ENotificationType.MESSAGE
            );
        }

        return updated;
    }

    @Override
    public RepairRequest getRepairRequestById(UUID id) {
        return repairRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RepairRequest not found with id " + id));
    }

    @Override
    public List<RepairRequest> getAllRepairRequests() {
        return repairRepository.findAll();
    }

    @Override
    public List<RepairRequest> getRepairRequestsByProperty(UUID propertyId) {
        return repairRepository.findByPropertyId(propertyId);
    }

    @Override
    public List<RepairRequest> getRepairRequestsByStatus(String status) {
        return repairRepository.findByStatus(status);
    }

    @Override
    public void deleteRepairRequest(UUID id) {
        RepairRequest repair = getRepairRequestById(id);
        repairRepository.delete(repair);
    }
}
