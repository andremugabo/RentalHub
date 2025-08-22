package rw.mugaboandre.rentalhub.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rw.mugaboandre.rentalhub.core.auth.dto.LoginRequest;
import rw.mugaboandre.rentalhub.core.auth.dto.LoginResponse;
import rw.mugaboandre.rentalhub.core.auth.dto.RegisterRequest;
import rw.mugaboandre.rentalhub.core.auth.dto.RegisterResponse;
import rw.mugaboandre.rentalhub.core.notification.service.INotificationService;
import rw.mugaboandre.rentalhub.core.person.model.Person;
import rw.mugaboandre.rentalhub.core.person.repository.IPersonRepository;
import rw.mugaboandre.rentalhub.core.person.service.IPersonService;
import rw.mugaboandre.rentalhub.core.util.enums.admin.ERole;
import rw.mugaboandre.rentalhub.core.util.enums.notifications.ENotificationType;
import rw.mugaboandre.rentalhub.core.util.enums.person.EContactPref;
import rw.mugaboandre.rentalhub.exception.DuplicateResourceException;
import rw.mugaboandre.rentalhub.exception.InvalidCredentialsException;
import rw.mugaboandre.rentalhub.exception.InvalidRoleException;
import rw.mugaboandre.rentalhub.exception.RegistrationFailedException;
import rw.mugaboandre.rentalhub.security.CustomUserPrincipal;
import rw.mugaboandre.rentalhub.security.JwtTokenProvider;

import java.util.HashSet;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user registration and authentication")
@RequiredArgsConstructor
public class AuthController {

    private final IPersonRepository personRepository;
    private final IPersonService personService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final INotificationService notificationService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Registers a new user with the specified role and details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data or role"),
            @ApiResponse(responseCode = "409", description = "Email, username, or phone already exists"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        // Validate duplicates
        if (personRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + request.getEmail());
        }
        if (personRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Username already exists: " + request.getUsername());
        }
        if (personRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException("Phone number already exists: " + request.getPhone());
        }

        // Validate password
//        if (request.getPassword() == null || request.getPassword().length() < 6 ||
//                !request.getPassword().matches(".*[A-Z].*") || !request.getPassword().matches(".*[0-9].*")) {
//            throw new RegistrationFailedException("Password must be at least 6 characters long with one uppercase letter and one number");
//        }

        // Create Person entity
        Person person = new Person();
        String roleInput = request.getRole() != null ? request.getRole().name() : "CLIENT";
        try {
            ERole eRole = ERole.valueOf(roleInput);
            person.setRole(eRole);
            person.setPermissions(request.getPermissions() != null ? request.getPermissions() : new HashSet<>());
            person.setFirstName(request.getFirstName());
            person.setLastName(request.getLastName());
            person.setEmail(request.getEmail());
            person.setUsername(request.getUsername());
            person.setPhone(request.getPhone());
            person.setPassword(request.getPassword());
            person.setContactPref(request.getContactPref() != null ? request.getContactPref() : EContactPref.EMAIL);

            Person savedPerson = personService.savePerson(person);

            // Send welcome notification
            notificationService.createNotification(
                    savedPerson,
                    "Welcome to RentalHub! We're excited to have you on board.",
                    ENotificationType.WELCOME_MESSAGE
            );

            RegisterResponse response = new RegisterResponse(
                    savedPerson.getId().toString(),
                    savedPerson.getUsername(),
                    savedPerson.getEmail(),
                    eRole.name()
            );

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            throw new InvalidRoleException("Invalid role value: " + roleInput);
        } catch (Exception ex) {
            throw new RegistrationFailedException("Failed to register user: " + ex.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate a user", description = "Authenticates a user and returns a JWT token with role and permissions")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User authenticated successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "403", description = "Account locked"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsernameOrEmail(),
                            request.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            CustomUserPrincipal userPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
            String jwt = jwtTokenProvider.generateToken(authentication);

            LoginResponse response = new LoginResponse(
                    userPrincipal.getId(),
                    userPrincipal.getUsername(),
                    userPrincipal.getEmail(),
                    userPrincipal.getRole(),
                    userPrincipal.getPermissions(),
                    jwt
            );

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<String> handleDuplicateResourceException(DuplicateResourceException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<String> handleInvalidRoleException(InvalidRoleException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(RegistrationFailedException.class)
    public ResponseEntity<String> handleRegistrationFailedException(RegistrationFailedException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
}