package staj.sigorta_uygulama_staj.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import staj.sigorta_uygulama_staj.Entities.Health;

public interface HealthRepository extends JpaRepository<Health,Long> {
}
