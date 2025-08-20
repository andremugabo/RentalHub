package rw.mugaboandre.rentalhub.controller.auth;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rw.mugaboandre.rentalhub.core.admin.model.Admin;
import rw.mugaboandre.rentalhub.core.client.model.Client;
import rw.mugaboandre.rentalhub.core.owner.model.Owner;
import rw.mugaboandre.rentalhub.core.person.model.Person;
import rw.mugaboandre.rentalhub.core.person.repository.PersonRepository;
import rw.mugaboandre.rentalhub.core.auth.dto.RegisterRequest;
import rw.mugaboandre.rentalhub.core.auth.dto.RegisterResponse;
import rw.mugaboandre.rentalhub.core.auth.dto.LoginRequest;
import rw.mugaboandre.rentalhub.core.auth.dto.LoginResponse;
import rw.mugaboandre.rentalhub.core.util.enums.admin.ERole;
import rw.mugaboandre.rentalhub.core.util.enums.person.EContactPref;
import rw.mugaboandre.rentalhub.exception.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import rw.mugaboandre.rentalhub.security.JwtTokenProvider;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user registration and authentication")
public class AuthController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Registers a new user (Admin, Client, or Owner) with the provided details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data or role"),
            @ApiResponse(responseCode = "409", description = "Email or username already exists"),
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

        // Create appropriate entity based on role
        Person person;
        String role;
        try {
            String roleInput = Optional.ofNullable(request.getRole())
                    .map(Object::toString)   // safely converts to String
                    .map(String::toUpperCase)
                    .orElse("CLIENT");
            switch (roleInput) {
                case "ADMIN":
                case "MANAGER":
                case "SUPERVISOR":
                    Admin admin = new Admin();
                    admin.setRole(ERole.valueOf(roleInput));
                    admin.setPermissions(request.getPermissions());
                    person = admin;
                    role = admin.getRole().name();
                    break;
                case "CLIENT":
                    Client client = new Client();
                    client.setPreferences(request.getPreferences());
                    person = client;
                    role = "CLIENT";
                    break;
                case "OWNER":
                    person = new Owner();
                    role = "OWNER";
                    break;
                default:
                    throw new InvalidRoleException("Invalid role: " + roleInput);
            }

            // Set common fields
            person.setFirstName(request.getFirstName());
            person.setLastName(request.getLastName());
            person.setEmail(request.getEmail());
            person.setUsername(request.getUsername());
            person.setPassword(passwordEncoder.encode(request.getPassword()));
            person.setPhone(request.getPhone());
            person.setContactPref(request.getContactPref() != null ? request.getContactPref() : EContactPref.EMAIL);

            // Save to database
            person = personRepository.save(person);
        } catch (IllegalArgumentException ex) {
            throw new InvalidRoleException("Invalid role value: " + request.getRole());
        } catch (Exception ex) {
            throw new RegistrationFailedException("Failed to register user: " + ex.getMessage());
        }

        // Create response
        RegisterResponse response = new RegisterResponse(
                person.getId().toString(),
                person.getUsername(),
                person.getEmail(),
                role
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate a user", description = "Authenticates a user and returns a JWT token")
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

            Person person = personRepository.findByUsernameOrEmail(
                    request.getUsernameOrEmail(),
                    request.getUsernameOrEmail()
            ).orElseThrow(() -> new InvalidCredentialsException("Invalid username or email"));

            // Check if account is locked (if you have such logic in Person entity)
            // if (person.isLocked()) {
            //     throw new AccountLockedException("Account is locked for user: " + request.getUsernameOrEmail());
            // }

            String jwt = jwtTokenProvider.generateToken(authentication);

            String roles = authentication.getAuthorities().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(","));

            LoginResponse response = new LoginResponse(
                    person.getId().toString(),
                    person.getUsername(),
                    person.getEmail(),
                    roles,
                    jwt
            );

            return ResponseEntity.ok(response);
        } catch (InvalidCredentialsException e) {
            throw e; // preserve explicit invalid credentials exception
        } catch (Exception ex) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }
}
