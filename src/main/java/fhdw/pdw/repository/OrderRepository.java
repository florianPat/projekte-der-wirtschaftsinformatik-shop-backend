package fhdw.pdw.repository;

import fhdw.pdw.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    public List<Order> findByUserId(int id);
}
