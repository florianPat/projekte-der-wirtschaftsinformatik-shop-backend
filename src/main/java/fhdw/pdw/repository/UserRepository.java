package fhdw.pdw.repository;

import fhdw.pdw.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByEmailIgnoreCase(String email);

  List<User> findByIdIn(List<Long> userIds);

  List<User> findAllByEmailIgnoreCase(String email);
}
