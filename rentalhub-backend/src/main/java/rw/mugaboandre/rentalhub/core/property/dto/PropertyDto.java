package rw.mugaboandre.rentalhub.core.property.dto;

import lombok.Data;
import rw.mugaboandre.rentalhub.core.util.enums.property.EAmenities;
import rw.mugaboandre.rentalhub.core.util.enums.property.EPropertyPreference;
import rw.mugaboandre.rentalhub.core.util.enums.property.EPropertyStatus;
import rw.mugaboandre.rentalhub.core.util.enums.property.EPropertyType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class PropertyDto {
    private EPropertyType type;
    private String address;
    private Double latitude;
    private Double longitude;
    private BigDecimal price;
    private EPropertyPreference propertyPreference;
    private EPropertyStatus status;
    private List<String> mediaUrls;
    private UUID ownerId;
    private UUID clientId;

    private String city;
    private String district;
    private List<EAmenities> amenities;
}
