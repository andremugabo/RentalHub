package rw.mugaboandre.rentalhub.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rw.mugaboandre.rentalhub.core.admin.model.Admin;
import rw.mugaboandre.rentalhub.core.person.model.Person;
import rw.mugaboandre.rentalhub.core.person.repository.PersonRepository;
import rw.mugaboandre.rentalhub.exception.ResourceNotFoundException;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Person person = personRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with username or email: " + usernameOrEmail));

        Set<GrantedAuthority> authorities = new HashSet<>();
        if (person instanceof Admin admin) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + admin.getRole().name()));
            if (admin.getPermissions() != null) {
                authorities.addAll(admin.getPermissions().stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList());
            }
        } else {
            // Default role for non-Admin (e.g., Client, Owner)
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new org.springframework.security.core.userdetails.User(
                person.getUsername(),
                person.getPassword(),
                authorities
        );
    }
}