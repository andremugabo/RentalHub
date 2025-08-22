package rw.mugaboandre.rentalhub.core.person.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.mugaboandre.rentalhub.core.person.model.Person;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPersonRepository extends JpaRepository<Person, UUID> {
    Optional<Person> findByUsernameOrEmail(String username, String email);

    boolean existsByEmail(@NotBlank(message = "Email is required") @Email(message = "Email must be valid") String email);

    boolean existsByUsername(@NotBlank(message = "Username is required") @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters") String username);

    boolean existsByPhone(@NotBlank(message = "Phone is required") String phone);

    Page<Person> findByActiveTrue(Pageable pageable);

    Page<Person> findByActiveFalse(Pageable pageable);

    Optional<Object> findByEmail(@Email String email);
}