package rw.mugaboandre.rentalhub.controller.notification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import rw.mugaboandre.rentalhub.core.notification.dto.NotificationRequest;
import rw.mugaboandre.rentalhub.core.notification.dto.NotificationResponse;
import rw.mugaboandre.rentalhub.core.notification.model.Notification;
import rw.mugaboandre.rentalhub.core.notification.service.INotificationService;
import rw.mugaboandre.rentalhub.core.person.model.Person;
import rw.mugaboandre.rentalhub.core.person.service.IPersonService;
import rw.mugaboandre.rentalhub.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "API for managing notifications in RentalHub")
public class NotificationController {

    private final INotificationService notificationService;
    private final IPersonService personService;

    @Operation(summary = "Create a new notification", description = "Creates a notification for a specific recipient")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Notification created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Recipient not found"),
            @ApiResponse(responseCode = "403", description = "Unauthorized access")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<NotificationResponse> createNotification(
            @Valid @RequestBody NotificationRequest request) {

        Person recipient = (Person) personService.findById(request.recipientId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with ID: " + request.recipientId()));

        Notification notification = notificationService.createNotification(
                recipient,
                request.message(),
                request.type()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(NotificationResponse.fromEntity(notification));
    }

    @Operation(summary = "Get notifications for a user", description = "Retrieves all notifications for a specific user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notifications retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Unauthorized access"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/user/{recipientId}")
    @PreAuthorize("principal?.id == #recipientId.toString() or hasRole('ADMIN')")
    public ResponseEntity<List<NotificationResponse>> getUserNotifications(
            @PathVariable UUID recipientId,
            Authentication authentication) {

        List<Notification> notifications = notificationService.getUserNotifications(recipientId);
        List<NotificationResponse> response = notifications.stream()
                .map(NotificationResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get a notification by ID", description = "Retrieves a specific notification by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notification retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Unauthorized access"),
            @ApiResponse(responseCode = "404", description = "Notification not found")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @notificationSecurity.isOwner(#id, principal.id)")
    public ResponseEntity<NotificationResponse> getNotificationById(@PathVariable UUID id) {
        return notificationService.getNotificationById(id)
                .map(notification -> ResponseEntity.ok(NotificationResponse.fromEntity(notification)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Mark a notification as read", description = "Marks a specific notification as read")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notification marked as read"),
            @ApiResponse(responseCode = "403", description = "Unauthorized access"),
            @ApiResponse(responseCode = "404", description = "Notification not found")
    })
    @PatchMapping("/{id}/read")
    @PreAuthorize("hasRole('ADMIN') or @notificationSecurity.isOwner(#id, principal.id)")
    public ResponseEntity<NotificationResponse> markNotificationAsRead(@PathVariable UUID id) {
        Notification updated = notificationService.markAsRead(id);
        return ResponseEntity.ok(NotificationResponse.fromEntity(updated));
    }
}
