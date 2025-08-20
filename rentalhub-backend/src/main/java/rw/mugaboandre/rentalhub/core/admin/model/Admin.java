package rw.mugaboandre.rentalhub.core.admin.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.mugaboandre.rentalhub.core.person.model.Person;
import rw.mugaboandre.rentalhub.core.util.enums.admin.ERole;

import java.util.Set;

@Entity
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Admin extends Person {
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ERole role;

    @ElementCollection
    private Set<String> permissions;

}