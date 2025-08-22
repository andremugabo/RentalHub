package rw.mugaboandre.rentalhub.core.person.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rw.mugaboandre.rentalhub.core.person.model.Person;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public interface IPersonService {
    Person savePerson(Person person);
    Person updatePerson(UUID id, Person person);
    Person updatePassword(UUID id, String password);
    void softDeletePerson(UUID id,boolean active);
    Optional<Person> findPersonById(UUID id);
    Page<Person> findAllActivePersons(Pageable pageable);
    Page<Person> findAllNonActivePersons(Pageable pageable);
    Page<Person> findAllPersons(Pageable pageable);
    void saveProfilePic(UUID id, MultipartFile file) throws IOException;
    byte[] getProfilePic(UUID id);

    Optional<Object> findById(@NotNull(message = "Recipient ID is required") UUID uuid);
}