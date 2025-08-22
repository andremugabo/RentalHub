package rw.mugaboandre.rentalhub.core.notification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import rw.mugaboandre.rentalhub.core.util.enums.notifications.ENotificationType;

import java.util.UUID;

public record NotificationRequest(
        @NotNull(message = "Recipient ID is required")
        UUID recipientId,
        @NotBlank(message = "Message is required")
        String message,
        @NotNull(message = "Notification type is required")
        ENotificationType type) {
}