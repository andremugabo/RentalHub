package rw.mugaboandre.rentalhub.core.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.mugaboandre.rentalhub.core.notification.model.Notification;
import rw.mugaboandre.rentalhub.core.person.model.Person;
import rw.mugaboandre.rentalhub.core.util.enums.notifications.ENotificationType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface INotificationRepository  extends JpaRepository<Notification, UUID> {

    List<Notification> findByRecipientId(UUID recipientId);
}
