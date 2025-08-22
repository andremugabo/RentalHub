package rw.mugaboandre.rentalhub.core.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rw.mugaboandre.rentalhub.core.notification.model.Notification;
import rw.mugaboandre.rentalhub.core.notification.repository.INotificationRepository;
import rw.mugaboandre.rentalhub.core.person.model.Person;
import rw.mugaboandre.rentalhub.core.util.enums.notifications.ENotificationStatus;
import rw.mugaboandre.rentalhub.core.util.enums.notifications.ENotificationType;
import rw.mugaboandre.rentalhub.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class NotificationImpl implements INotificationService{

    private final INotificationRepository notificationRepository;

    @Override
    public Notification createNotification(Person recipient, String message, ENotificationType type) {
        Notification notification = new Notification();
        notification.setRecipient(recipient);
        notification.setMessage(message);
        notification.setNotificationType(type);
        notification.setSentDate(LocalDateTime.now());
        notification.setStatus(ENotificationStatus.SENT);
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getUserNotifications(UUID recipientId) {
        // Assuming a custom query method is added (see below)
        return notificationRepository.findByRecipientId(recipientId);
    }

    @Override
    public Optional<Notification> getNotificationById(UUID id) {
        return notificationRepository.findById(id);
    }
    @Override
    public Notification updateNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getNotificationsByRecipientId(UUID recipientId) {
        return notificationRepository.findByRecipientId(recipientId);
    }

    @Override
    public Notification markAsRead(UUID id) {
        Notification notification = getNotificationById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with ID: " + id));

        notification.setRead(true);
        return updateNotification(notification);
    }



}
