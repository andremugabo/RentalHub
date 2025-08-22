package rw.mugaboandre.rentalhub.core.property.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.mugaboandre.rentalhub.core.base.AbstractBaseEntity;
import rw.mugaboandre.rentalhub.core.client.model.Client;
import rw.mugaboandre.rentalhub.core.leaseContract.model.LeaseContract;
import rw.mugaboandre.rentalhub.core.owner.model.Owner;
import rw.mugaboandre.rentalhub.core.repairRequest.model.RepairRequest;
import rw.mugaboandre.rentalhub.core.util.enums.property.EAmenities;
import rw.mugaboandre.rentalhub.core.util.enums.property.EPropertyPreference;
import rw.mugaboandre.rentalhub.core.util.enums.property.EPropertyStatus;
import rw.mugaboandre.rentalhub.core.util.enums.property.EPropertyType;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "properties") // explicit table name
public class Property extends AbstractBaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private EPropertyType type;

    @NotBlank(message = "Address cannot be blank")
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;


    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private EPropertyPreference propertyPreference;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EPropertyStatus status = EPropertyStatus.AVAILABLE; // default

    @ElementCollection
    @CollectionTable(name = "property_media", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "url", nullable = false)
    private List<String> mediaUrls;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "property_amenities", joinColumns = @JoinColumn(name = "property_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "amenity")
    private List<EAmenities> amenities;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "district", nullable = false)
    private String district;



    @OneToMany(
            mappedBy = "property",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<LeaseContract> leaseContracts;

    @OneToMany(
            mappedBy = "property",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<RepairRequest> repairRequests;
}
