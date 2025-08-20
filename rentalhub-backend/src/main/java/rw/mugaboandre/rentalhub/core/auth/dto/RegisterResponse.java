package rw.mugaboandre.rentalhub.core.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor  @NoArgsConstructor
public class RegisterResponse {
    private String id;
    private String username;
    private String email;
    private String role;



}