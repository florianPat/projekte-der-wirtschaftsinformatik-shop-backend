package fhdw.pdw.repository;

import fhdw.pdw.model.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
  public List<Order> findByUserId(int id);
}
