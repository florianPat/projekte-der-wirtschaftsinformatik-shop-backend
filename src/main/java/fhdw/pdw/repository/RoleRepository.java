package fhdw.pdw.repository;

import fhdw.pdw.model.Role;
import fhdw.pdw.model.RoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
  /**
   * Sucht eine Rolle nach dem Namen aus der Datenbank
   */
  Optional<Role> findByName(RoleName roleName);
}
