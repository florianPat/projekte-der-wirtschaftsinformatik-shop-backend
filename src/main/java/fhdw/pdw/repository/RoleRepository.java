package fhdw.pdw.repository;

import fhdw.pdw.model.Role;
import fhdw.pdw.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleName roleName);
}
