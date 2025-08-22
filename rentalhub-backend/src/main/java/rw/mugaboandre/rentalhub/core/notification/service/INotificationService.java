package rw.mugaboandre.rentalhub.core.notification.service;

import rw.mugaboandre.rentalhub.core.notification.model.Notification;
import rw.mugaboandre.rentalhub.core.person.model.Person;
import rw.mugaboandre.rentalhub.core.util.enums.notifications.ENotificationType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface INotificationService {
    Notification createNotification(Person recipient, String message, ENotificationType type);
    List<Notification> getUserNotifications(UUID recipientId);
    Optional<Notification> getNotificationById(UUID id);
    Object updateNotification(Notification notification);
    List<Notification> getNotificationsByRecipientId(UUID recipientId);
    Notification markAsRead(UUID id);

}
