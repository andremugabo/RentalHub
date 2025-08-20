package rw.mugaboandre.rentalhub.core.leaseContract.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.mugaboandre.rentalhub.core.base.AbstractBaseEntity;
import rw.mugaboandre.rentalhub.core.client.model.Client;
import rw.mugaboandre.rentalhub.core.property.model.Property;
import rw.mugaboandre.rentalhub.core.util.enums.leaseContract.ELeaseStatus;

import java.time.LocalDate;

@Entity
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class LeaseContract extends AbstractBaseEntity {
    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "terms")
    private String terms;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ELeaseStatus status;

    @ManyToOne
    private Property property;

    @ManyToOne
    private Client client;


}