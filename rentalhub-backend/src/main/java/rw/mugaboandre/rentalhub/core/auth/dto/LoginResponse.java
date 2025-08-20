package rw.mugaboandre.rentalhub.core.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class LoginResponse {
    private String id;
    private String username;
    private String email;
    private String roles;
    private String token;
}