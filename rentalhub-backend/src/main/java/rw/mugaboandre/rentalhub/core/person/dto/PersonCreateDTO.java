package rw.mugaboandre.rentalhub.core.person.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.mugaboandre.rentalhub.core.util.enums.admin.ERole;
import rw.mugaboandre.rentalhub.core.util.enums.person.EContactPref;

import java.util.Set;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class PersonCreateDTO {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Role is required")
    private ERole role;


    @NotNull(message = "Contact preference is required")
    private EContactPref contactPref;

    private Set<String> permissions;



}
