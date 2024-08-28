package staj.sigorta_uygulama_staj.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import staj.sigorta_uygulama_staj.Entities.Role;
import staj.sigorta_uygulama_staj.Entities.Users;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);
    List<Users> findByRolesContaining(Role role);

}
