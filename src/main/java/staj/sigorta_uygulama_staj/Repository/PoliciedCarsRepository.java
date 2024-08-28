package staj.sigorta_uygulama_staj.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import staj.sigorta_uygulama_staj.Entities.PoliciedCars;

public interface PoliciedCarsRepository extends JpaRepository<PoliciedCars,Long> {
}
