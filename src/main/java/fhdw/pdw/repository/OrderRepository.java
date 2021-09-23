package fhdw.pdw.repository;

import fhdw.pdw.model.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
  /**
   * Sucht alle Bestellungen eines Benutzers aus der Datenbank
   */
  public List<Order> findByUserId(int id);
}
