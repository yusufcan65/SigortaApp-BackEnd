package staj.sigorta_uygulama_staj.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import staj.sigorta_uygulama_staj.Entities.Policy;

import java.util.List;

public interface PolicyRepository extends JpaRepository<Policy,Long> {

    boolean existsByPolicyNumber(Integer policyNumber);

    List<Policy> findByCustomerId(Long customer_id);
    List<Policy> findByStatus(String status);
    @Query("SELECT p FROM Policy p WHERE p.status = 'T' ORDER BY p.id DESC")
    List<Policy> findTop10Offers(Pageable pageable);

    List<Policy> findByUserId(long userId);


    @Query("SELECT COUNT(p) FROM Policy p WHERE p.status ='T'")
    long countByOffers();;

    @Query("SELECT COUNT(p) FROM Policy p WHERE p.status = 'P'")
    long countByStatusP();

}
