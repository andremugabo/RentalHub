package rw.mugaboandre.rentalhub.core.notification.dto;

import rw.mugaboandre.rentalhub.core.notification.model.Notification;
import rw.mugaboandre.rentalhub.core.util.enums.notifications.ENotificationStatus;
import rw.mugaboandre.rentalhub.core.util.enums.notifications.ENotificationType;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationResponse(
        UUID id,
        String message,
        LocalDateTime sentDate,
        ENotificationStatus status,
        ENotificationType type,
        UUID recipientId,
        boolean read) {

    public static NotificationResponse fromEntity(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getMessage(),
                notification.getSentDate(),
                notification.getStatus(),
                notification.getNotificationType(),
                notification.getRecipient().getId(),
                notification.isRead()
        );
    }
}