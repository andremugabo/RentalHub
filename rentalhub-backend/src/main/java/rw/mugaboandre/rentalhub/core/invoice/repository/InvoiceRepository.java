package rw.mugaboandre.rentalhub.core.invoice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.mugaboandre.rentalhub.core.invoice.model.Invoice;

import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
}
