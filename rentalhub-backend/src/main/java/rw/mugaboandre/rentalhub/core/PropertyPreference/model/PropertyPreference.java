package rw.mugaboandre.rentalhub.core.PropertyPreference.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import rw.mugaboandre.rentalhub.core.base.AbstractBaseEntity;
import rw.mugaboandre.rentalhub.core.client.model.Client;
import rw.mugaboandre.rentalhub.core.util.enums.property.EAmenities;
import rw.mugaboandre.rentalhub.core.util.enums.property.EPropertyPreference;
import rw.mugaboandre.rentalhub.core.util.enums.property.EPropertyType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class PropertyPreference extends AbstractBaseEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private EPropertyPreference propertyPreference; // e.g. APARTMENT, HOUSE, VILLA

    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    private String city;
    private String district;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "property_preference_amenities",
            joinColumns = @JoinColumn(name = "preference_id")
    )
    @Enumerated(EnumType.STRING)
    private List<EAmenities> amenities; // e.g. WIFI, PARKING, GYM

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
