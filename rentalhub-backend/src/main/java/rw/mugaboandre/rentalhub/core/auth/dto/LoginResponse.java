package rw.mugaboandre.rentalhub.core.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class LoginResponse {
    private String id;
    private String username;
    private String email;
    private String role;
    private Set<String> permissions;
    private String token;
}