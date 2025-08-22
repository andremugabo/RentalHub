package rw.mugaboandre.rentalhub.core.repairRequest.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.mugaboandre.rentalhub.core.base.AbstractBaseEntity;
import rw.mugaboandre.rentalhub.core.property.model.Property;
import rw.mugaboandre.rentalhub.core.util.enums.repairRequest.ERepairStatus;

import java.math.BigDecimal;

@Entity
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class RepairRequest extends AbstractBaseEntity {
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ERepairStatus status = ERepairStatus.PENDING;

    @Column(name = "assigned_team")
    private String assignedTeam;

    @Column(name = "estimated_cost")
    private BigDecimal estimatedCost;

    @Column(name = "actual_cost")
    private BigDecimal actualCost;

    @ManyToOne
    private Property property;


}