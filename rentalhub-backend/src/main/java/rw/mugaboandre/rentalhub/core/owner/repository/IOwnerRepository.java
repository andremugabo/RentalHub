package rw.mugaboandre.rentalhub.core.owner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.mugaboandre.rentalhub.core.owner.model.Owner;

import java.util.UUID;

public interface IOwnerRepository extends JpaRepository<Owner, UUID> {
}
