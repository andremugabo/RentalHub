package rw.mugaboandre.rentalhub.core.notification.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.mugaboandre.rentalhub.core.base.AbstractBaseEntity;
import rw.mugaboandre.rentalhub.core.person.model.Person;
import rw.mugaboandre.rentalhub.core.util.enums.notifications.ENotificationStatus;
import rw.mugaboandre.rentalhub.core.util.enums.notifications.ENotificationType;

import java.time.LocalDateTime;

@Entity
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Notification extends AbstractBaseEntity {
    @Column(name = "message")
    private String message;

    @Column(name = "sent_date")
    private LocalDateTime sentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ENotificationStatus status;

    @Enumerated(EnumType.STRING)
    private ENotificationType notificationType;

    @ManyToOne
    @JsonBackReference
    private Person recipient;

    @Column(name = "read")
    private boolean read = Boolean.FALSE;


}