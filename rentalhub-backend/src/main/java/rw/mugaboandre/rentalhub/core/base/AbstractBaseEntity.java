package rw.mugaboandre.rentalhub.core.base;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;


@MappedSuperclass
@Setter @Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractBaseEntity extends  AbstractAuditEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "is_active")
    private boolean isActive = Boolean.TRUE;

}