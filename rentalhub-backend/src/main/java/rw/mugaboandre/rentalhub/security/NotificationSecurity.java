package rw.mugaboandre.rentalhub.security;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rw.mugaboandre.rentalhub.core.notification.service.INotificationService;

import java.util.UUID;

@Component("notificationSecurity")
@RequiredArgsConstructor
public class NotificationSecurity {

    private final INotificationService notificationService;

    public boolean isOwner(UUID notificationId, String principalId) {
        return notificationService.getNotificationById(notificationId)
                .map(n -> n.getRecipient().getId().toString().equals(principalId))
                .orElse(false);
    }
}
