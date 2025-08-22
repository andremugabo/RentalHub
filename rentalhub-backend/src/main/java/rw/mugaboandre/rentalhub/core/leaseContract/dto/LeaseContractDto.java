package rw.mugaboandre.rentalhub.core.leaseContract.dto;


import lombok.Data;
import rw.mugaboandre.rentalhub.core.util.enums.leaseContract.ELeaseStatus;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class LeaseContractDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private String terms;
    private ELeaseStatus status;
    private UUID propertyId;
    private UUID clientId;
}
