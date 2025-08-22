package rw.mugaboandre.rentalhub.core.PropertyPreference.dto;


import lombok.Data;
import rw.mugaboandre.rentalhub.core.util.enums.property.EAmenities;
import rw.mugaboandre.rentalhub.core.util.enums.property.EPropertyPreference;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class PropertyPreferenceDto {

    private UUID id;

    private EPropertyPreference propertyPreference;

    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    private String city;
    private String district;

    private List<EAmenities> amenities; // e.g. WIFI, PARKING, GYM

    private UUID clientId;
}
