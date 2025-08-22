package rw.mugaboandre.rentalhub.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rw.mugaboandre.rentalhub.core.person.model.Person;
import rw.mugaboandre.rentalhub.core.person.repository.IPersonRepository;
import rw.mugaboandre.rentalhub.core.util.enums.admin.ERole;
import rw.mugaboandre.rentalhub.exception.ResourceNotFoundException;
import rw.mugaboandre.rentalhub.security.CustomUserPrincipal;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final IPersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // Fetch person by username or email
        Person person = personRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with username or email: " + usernameOrEmail));

        // Initialize authorities and permissions
        Set<GrantedAuthority> authorities = new HashSet<>();
        Set<String> permissions = new HashSet<>();

        // Assign role from ERole enum
        String role = "ROLE_" + (person.getRole() != null ? person.getRole().name() : ERole.CLIENT.name());
        authorities.add(new SimpleGrantedAuthority(role));

        // Add permissions as authorities if they exist
        if (person.getPermissions() != null && !person.getPermissions().isEmpty()) {
            permissions.addAll(person.getPermissions());
            person.getPermissions().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));
        }

        // Return CustomUserPrincipal
        return new CustomUserPrincipal(
                person.getId().toString(),
                person.getUsername(),
                person.getEmail(),
                person.getPassword(),
                role,
                permissions,
                authorities
        );
    }
}