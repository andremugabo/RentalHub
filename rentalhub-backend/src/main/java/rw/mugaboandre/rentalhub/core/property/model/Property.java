package rw.mugaboandre.rentalhub.core.property.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.mugaboandre.rentalhub.core.base.AbstractBaseEntity;
import rw.mugaboandre.rentalhub.core.client.model.Client;
import rw.mugaboandre.rentalhub.core.leaseContract.model.LeaseContract;
import rw.mugaboandre.rentalhub.core.owner.model.Owner;
import rw.mugaboandre.rentalhub.core.repairRequest.model.RepairRequest;
import rw.mugaboandre.rentalhub.core.util.enums.property.EPropertyStatus;
import rw.mugaboandre.rentalhub.core.util.enums.property.EPropertyType;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Property extends AbstractBaseEntity {
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EPropertyType type;


    @Column(name = "address")
    private String address;

    @Column(name = "price")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EPropertyStatus status;

    @ElementCollection
    private List<String> mediaUrls;

    @ManyToOne
    private Owner owner;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "property")
    private List<LeaseContract> leaseContracts;

    @OneToMany(mappedBy = "property")
    private List<RepairRequest> repairRequests;


}