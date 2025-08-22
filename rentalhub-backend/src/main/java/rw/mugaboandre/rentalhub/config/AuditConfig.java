package rw.mugaboandre.rentalhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import rw.mugaboandre.rentalhub.security.CustomUserPrincipal;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            System.out.println("=== AUDIT DEBUG ===");
            System.out.println("Authentication: " + authentication);
            if (authentication != null) {
                System.out.println("Authenticated: " + authentication.isAuthenticated());
                System.out.println("Principal: " + authentication.getPrincipal());
                System.out.println("Principal class: " + authentication.getPrincipal().getClass().getName());
                System.out.println("Name: " + authentication.getName());

                if (authentication.getPrincipal() instanceof CustomUserPrincipal userPrincipal) {
                    System.out.println("User ID: " + userPrincipal.getId());
                    System.out.println("Username: " + userPrincipal.getUsername());
                }
            }
            System.out.println("===================");

            if (authentication == null || !authentication.isAuthenticated()
                    || "anonymousUser".equals(authentication.getPrincipal())) {
                return Optional.of("SYSTEM");
            }

            if (authentication.getPrincipal() instanceof CustomUserPrincipal userPrincipal) {
                return Optional.of(userPrincipal.getId());
            }

            return Optional.of(authentication.getName());
        };
    }
}
