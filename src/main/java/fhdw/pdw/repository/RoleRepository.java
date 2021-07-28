package fhdw.pdw.repository;

import fhdw.pdw.model.Role;
import fhdw.pdw.model.RoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
  Optional<Role> findByName(RoleName roleName);
}
