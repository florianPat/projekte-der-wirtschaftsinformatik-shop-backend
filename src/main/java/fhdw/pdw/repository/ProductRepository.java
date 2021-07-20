package fhdw.pdw.repository;

import fhdw.pdw.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
  public Product findByName(String name);
}
