package rw.mugaboandre.rentalhub.security;


import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Getter
public class CustomUserPrincipal extends User {
    private final String id;
    private final String email;
    private final String role;
    private final Set<String> permissions;

    public CustomUserPrincipal(String id, String username, String email, String password,
                               String role, Set<String> permissions,
                               Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
        this.email = email;
        this.role = role;
        this.permissions = permissions;
    }
}