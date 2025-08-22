package rw.mugaboandre.rentalhub.controller.person;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rw.mugaboandre.rentalhub.core.notification.service.INotificationService;
import rw.mugaboandre.rentalhub.core.person.model.Person;
import rw.mugaboandre.rentalhub.core.person.service.IPersonService;
import rw.mugaboandre.rentalhub.core.person.dto.PersonCreateDTO;
import rw.mugaboandre.rentalhub.core.util.enums.notifications.ENotificationType;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000") // Restrict for production
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/persons")
@Tag(name = "Person Management", description = "Operations for managing Person entities")
public class PersonController {

    private final IPersonService personService;
    private final INotificationService notificationService;
    private final BCryptPasswordEncoder passwordEncoder;
    // Create a new person (e.g., registration) with DTO
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create a Person",
            description = "Create a new person record",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @PostMapping("/create")
    public ResponseEntity<Person> createPerson(@Valid @RequestBody PersonCreateDTO personDTO) {
        Person person = mapToEntity(personDTO);
        Person savedPerson = personService.savePerson(person);
        try {
            if (notificationService == null) {
                throw
                         new NoSuchElementException("NotificationService is not injected for person ID {}"+ savedPerson.getId());
            } else {
                notificationService.createNotification(
                        savedPerson,
                        String.format("Welcome to RentalHub, %s! We're excited to have you on board.",
                                savedPerson.getFirstName() != null ? savedPerson.getFirstName() : "User"),
                        ENotificationType.WELCOME_MESSAGE
                );
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to send welcome notification for person ID {}: {}");
        }
        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }


    // Update a person
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    @Operation(
            summary = "Update a Person by ID",
            description = "Update the details of a person by ID",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable UUID id, @RequestBody Person person) {
        Person updatedPerson = personService.updatePerson(id, person);
        // Send profile updated notification with null safety
        notificationService.createNotification(
                updatedPerson,
                String.format("Your profile has been updated, %s.",
                        updatedPerson.getFirstName() != null ? updatedPerson.getFirstName() : "User"),
                ENotificationType.PROFILE_UPDATED
        );
        return ResponseEntity.ok(updatedPerson);
    }

    // Update a person's password
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    @Operation(
            summary = "Update a Person's Password",
            description = "Update the password for a person by ID",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @PutMapping("/{id}/password")
    public ResponseEntity<Person> updatePassword(@PathVariable UUID id, @RequestBody String password) {
        Person updatedPerson = personService.updatePassword(id, password);
        // Send password updated notification
        notificationService.createNotification(
                updatedPerson,
                "Your password has been successfully updated.",
                ENotificationType.PROFILE_UPDATED
        );
        return ResponseEntity.ok(updatedPerson);
    }

    // Soft delete a person
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Soft Delete a Person",
            description = "Soft delete a person by setting active status to false",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeletePerson(@PathVariable UUID id, boolean active) {
        personService.softDeletePerson(id,active);
        // Send account deactivation notification
        Optional<Person> person = personService.findPersonById(id);
        person.ifPresent(p -> notificationService.createNotification(
                p,
                "Your account has been deactivated.",
                ENotificationType.ACCOUNT_VERIFICATION
        ));
        return ResponseEntity.noContent().build();
    }

    // Get a person by ID
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    @Operation(
            summary = "Get a Person by ID",
            description = "Retrieve a person by their ID",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Person>> getPersonById(@PathVariable UUID id) {
        Optional<Person> person = personService.findPersonById(id);
        return ResponseEntity.ok(person);
    }

    // Get all active persons with pagination
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Get All Active Persons",
            description = "Retrieve all active persons with pagination",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @GetMapping("/active")
    public ResponseEntity<Page<Person>> getAllActivePersons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Person> personPage = personService.findAllActivePersons(pageable);
        return ResponseEntity.ok(personPage);
    }

    // Get all non-active persons with pagination
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Get All Non-Active Persons",
            description = "Retrieve all non-active (soft deleted) persons with pagination",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @GetMapping("/non-active")
    public ResponseEntity<Page<Person>> getAllNonActivePersons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Person> personPage = personService.findAllNonActivePersons(pageable);
        return ResponseEntity.ok(personPage);
    }

    // Get all persons with pagination
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Get All Persons",
            description = "Retrieve all persons regardless of active status with pagination",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @GetMapping
    public ResponseEntity<Page<Person>> getAllPersons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Person> personPage = personService.findAllPersons(pageable);
        return ResponseEntity.ok(personPage);
    }

    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    @Operation(
            summary = "Upload Profile Picture",
            description = "Upload a profile picture for a person by ID",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @PostMapping("/{id}/upload-pic")
    public ResponseEntity<String> uploadProfilePic(
            @PathVariable UUID id,
            @RequestParam("file") MultipartFile file) {
        try {
            personService.saveProfilePic(id, file);
            Optional<Person> person = personService.findPersonById(id);
            person.ifPresent(p -> notificationService.createNotification(
                    p,
                    "Your profile picture has been updated.",
                    ENotificationType.PROFILE_UPDATED
            ));
            return ResponseEntity.ok("Profile picture uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload picture: " + e.getMessage());
        }
    }

    // Get profile picture
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    @Operation(
            summary = "Get Profile Picture",
            description = "Retrieve the profile picture for a person by ID",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @GetMapping("/{id}/profile-pic")
    public ResponseEntity<byte[]> getProfilePic(@PathVariable UUID id) {
        byte[] image = personService.getProfilePic(id);
        String contentType = determineContentType(image);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType != null ? contentType : "application/octet-stream")
                .body(image);
    }

    private String determineContentType(byte[] image) {
        if (image == null || image.length < 2) return null;
        if (image[0] == (byte) 0xFF && image[1] == (byte) 0xD8) return "image/jpeg";
        if (image[0] == (byte) 0x89 && image[1] == (byte) 0x50) return "image/png";
        if (image[0] == (byte) 0x47 && image[1] == (byte) 0x49) return "image/gif";
        return null;
    }

    private Person mapToEntity(PersonCreateDTO dto) {
        Person person = new Person();
        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person.setEmail(dto.getEmail());
        person.setUsername(dto.getUsername());
        person.setPhone(dto.getPhone());
        person.setPassword(passwordEncoder.encode(dto.getPassword())); // To be encoded in service
        person.setRole(dto.getRole());
        person.setPermissions(dto.getPermissions());
        person.setContactPref(dto.getContactPref());
        return person;
    }
}