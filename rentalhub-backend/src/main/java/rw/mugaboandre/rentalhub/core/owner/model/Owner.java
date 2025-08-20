package rw.mugaboandre.rentalhub.core.owner.model;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.mugaboandre.rentalhub.core.person.model.Person;
import rw.mugaboandre.rentalhub.core.property.model.Property;

import java.util.List;

@Entity
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Owner extends Person {
    @OneToMany(mappedBy = "owner")
    private List<Property> propertiesOwned;

}