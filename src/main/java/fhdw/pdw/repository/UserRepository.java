package fhdw.pdw.repository;

import fhdw.pdw.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
  /**
   * Sucht einen Benutzer mit der gleichen E-Mail-Adresse aus der Datenbank
   */
  Optional<User> findByEmailIgnoreCase(String email);

  /**
   * Sucht nach einer Menge von Benutzern mit den Id`s aus der Datenbank
   */
  List<User> findByIdIn(List<Long> userIds);

  /**
   * Sucht einen Benutzer mit der gleichen E-Mail-Adresse, wobei Groß- und Kleinschreibung
   * nicht beachtet wird, aus der Datenbank (für die Registierung oder den Login nützlich)
   */
  List<User> findAllByEmailIgnoreCase(String email);
}
