package rw.mugaboandre.rentalhub.core.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import rw.mugaboandre.rentalhub.core.util.enums.person.EContactPref;
import rw.mugaboandre.rentalhub.core.util.enums.admin.ERole;

import java.util.Set;

@Setter
@Getter
public class RegisterRequest {
    @NotBlank(message = "First name is required")
    @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;

    @NotBlank(message = "Phone is required")
    @Size(min = 10, max = 15, message = "Phone must be between 10 and 15 characters")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone must be a valid phone number")
    private String phone;

    @NotNull(message = "Contact preference is required")
    private EContactPref contactPref;

    @NotNull(message = "Role is required")
    private ERole role;

    private Set<String> permissions;
}