package staj.sigorta_uygulama_staj.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import staj.sigorta_uygulama_staj.Entities.Cars;

import java.util.List;

public interface CarRepository extends JpaRepository<Cars,Long> {

    Cars findById(long id);

    @Query("SELECT DISTINCT c.brand FROM Cars c")
    List<String> findDistinctBrands();

    @Query("SELECT DISTINCT c.model FROM Cars c WHERE c.brand = :brand")
    List<String> findByBrand(@Param("brand") String brand);

    @Query("SELECT DISTINCT c.modelYear FROM Cars c WHERE c.brand = :brand AND c.model = :model")
    List<Integer> findDistinctModelYearByBrandAndModel(@Param("brand") String brand, @Param("model") String model);

    Cars findByBrandAndModelAndModelYear(String brand, String model, Integer modelYear);

}
