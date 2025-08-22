package rw.mugaboandre.rentalhub.core.person.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rw.mugaboandre.rentalhub.core.client.model.Client;
import rw.mugaboandre.rentalhub.core.client.repository.IClientRepository;
import rw.mugaboandre.rentalhub.core.owner.model.Owner;
import rw.mugaboandre.rentalhub.core.owner.repository.IOwnerRepository;
import rw.mugaboandre.rentalhub.core.person.model.Person;
import rw.mugaboandre.rentalhub.core.person.repository.IPersonRepository;
import rw.mugaboandre.rentalhub.exception.ResourceAlreadyExistsException;
import rw.mugaboandre.rentalhub.exception.ResourceNotFoundException;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements IPersonService {
    private final IPersonRepository personRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final IOwnerRepository ownerRepository;
    private final IClientRepository clientRepository;

    @Override
    public Person updatePerson(UUID id, Person person) {
        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with ID: " + id));
        if (person.getEmail() != null && !person.getEmail().equals(existingPerson.getEmail())) {
            if (personRepository.findByEmail(person.getEmail()).isPresent()) {
                throw new ResourceAlreadyExistsException("Email " + person.getEmail() + " is already registered");
            }
            existingPerson.setEmail(person.getEmail());
        }
        if (person.getFirstName() != null) existingPerson.setFirstName(person.getFirstName());
        if (person.getLastName() != null) existingPerson.setLastName(person.getLastName());
        if (person.getPhone() != null) existingPerson.setPhone(person.getPhone());
        if (person.getUsername() != null) existingPerson.setUsername(person.getUsername());
        if (person.getPassword() != null) existingPerson.setPassword(passwordEncoder.encode(person.getPassword()));
        if (person.getRole() != null) existingPerson.setRole(person.getRole());
        if (person.getPermissions() != null) existingPerson.setPermissions(person.getPermissions());
        if (person.getContactPref() != null) existingPerson.setContactPref(person.getContactPref());
        return personRepository.save(existingPerson);
    }

    @Override
    public void softDeletePerson(UUID id, boolean active) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with ID: " + id));
        person.setActive(active);
        personRepository.save(person);
    }

    @Override
    public Optional<Person> findPersonById(UUID id) {
        return personRepository.findById(id);
    }

    @Override
    public Page<Person> findAllActivePersons(Pageable pageable) {
        return personRepository.findByActiveTrue(pageable);
    }

    @Override
    public Page<Person> findAllNonActivePersons(Pageable pageable) {
        return personRepository.findByActiveFalse(pageable);
    }

    @Override
    public Page<Person> findAllPersons(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Person savePerson(Person person) {
        if (person.getEmail() == null) {
            throw new IllegalArgumentException("Email is required for new person");
        }
        if (personRepository.findByEmail(person.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email " + person.getEmail() + " is already registered");
        }

        // Encode password
        person.setPassword(passwordEncoder.encode(person.getPassword()));

        // Save subclass based on role
        switch (person.getRole()) {
            case OWNER -> {
                Owner owner = new Owner();
                copyPersonFields(person, owner);
                return ownerRepository.save(owner);
            }
            case CLIENT -> {
                Client client = new Client();
                copyPersonFields(person, client);
                return clientRepository.save(client);
            }
            default -> {
                return personRepository.save(person);
            }
        }
    }

    private void copyPersonFields(Person source, Person target) {
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setEmail(source.getEmail());
        target.setUsername(source.getUsername());
        target.setPhone(source.getPhone());
        target.setProfilePic(source.getProfilePic());
        target.setPassword(source.getPassword());
        target.setRole(source.getRole());
        target.setPermissions(source.getPermissions());
        target.setContactPref(source.getContactPref());
    }


    @Override
    public Optional<Object> findById(UUID id) {
        return Optional.of(personRepository.findById(id));
    }

    @Override
    public void saveProfilePic(UUID personId, MultipartFile file) throws IOException {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with ID: " + personId));
        if (file != null && !file.isEmpty()) {
            person.setProfilePic(file.getBytes());
        }
        personRepository.save(person);
    }

    @Override
    public byte[] getProfilePic(UUID personId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with ID: " + personId));
        return person.getProfilePic() != null ? person.getProfilePic() : new byte[0];
    }

    @Override
    public Person updatePassword(UUID id, String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
//        if (!password.matches(".*[A-Z].*") || !password.matches(".*[0-9].*")) {
//            throw new IllegalArgumentException("Password must contain at least one uppercase letter and one number");
//        }

        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with ID: " + id));
        person.setPassword(passwordEncoder.encode(password));
        return personRepository.save(person);
    }
}