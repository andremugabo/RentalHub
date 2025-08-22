package rw.mugaboandre.rentalhub.core.client.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.mugaboandre.rentalhub.core.PropertyPreference.model.PropertyPreference;
import rw.mugaboandre.rentalhub.core.leaseContract.model.LeaseContract;
import rw.mugaboandre.rentalhub.core.payment.model.Payment;
import rw.mugaboandre.rentalhub.core.person.model.Person;

import java.util.List;

@Entity
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Client extends Person {
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PropertyPreference> preferences;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<LeaseContract> leaseHistory;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Payment> transactionLog;



}