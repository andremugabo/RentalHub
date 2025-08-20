package rw.mugaboandre.rentalhub.core.person.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import rw.mugaboandre.rentalhub.core.base.AbstractBaseEntity;
import rw.mugaboandre.rentalhub.core.notification.model.Notification;
import rw.mugaboandre.rentalhub.core.util.enums.person.EContactPref;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class Person extends AbstractBaseEntity {
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "phone", nullable = false ,unique = true)
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "contact_pref", nullable = false)
    private EContactPref contactPref = EContactPref.EMAIL;

    @OneToMany(mappedBy = "recipient")
    private List<Notification> notifications;
}