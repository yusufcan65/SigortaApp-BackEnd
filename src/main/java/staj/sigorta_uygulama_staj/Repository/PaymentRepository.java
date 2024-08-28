package staj.sigorta_uygulama_staj.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import staj.sigorta_uygulama_staj.Entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
