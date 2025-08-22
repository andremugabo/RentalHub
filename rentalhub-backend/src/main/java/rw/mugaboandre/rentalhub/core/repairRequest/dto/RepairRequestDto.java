package rw.mugaboandre.rentalhub.core.repairRequest.dto;

import lombok.Data;
import rw.mugaboandre.rentalhub.core.util.enums.repairRequest.ERepairStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class RepairRequestDto {
    private String description;
    private ERepairStatus status;
    private String assignedTeam;
    private BigDecimal estimatedCost;
    private BigDecimal actualCost;
    private UUID propertyId;
    private UUID clientId;
}
