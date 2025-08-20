package rw.mugaboandre.rentalhub.core.client.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.mugaboandre.rentalhub.core.leaseContract.model.LeaseContract;
import rw.mugaboandre.rentalhub.core.payment.model.Payment;
import rw.mugaboandre.rentalhub.core.person.model.Person;

import java.util.List;

@Entity
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Client extends Person {
    @Column(name = "preferences")
    private String preferences;

    @OneToMany(mappedBy = "client")
    private List<LeaseContract> leaseHistory;

    @OneToMany(mappedBy = "client")
    private List<Payment> transactionLog;


}