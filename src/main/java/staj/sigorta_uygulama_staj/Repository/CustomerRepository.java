package staj.sigorta_uygulama_staj.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import staj.sigorta_uygulama_staj.Entities.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.id_number = :id_number")
    Optional<Customer> findByIdNumber(@Param("id_number") String id_number);
    List<Customer> findByUserId(long user_id);
    boolean existsByCustomerNumber(Integer customerNumber);
    Customer findByName(String name);


    Customer findById(long id);
    Customer findByCustomerNumber(Integer customerNumber);



}
