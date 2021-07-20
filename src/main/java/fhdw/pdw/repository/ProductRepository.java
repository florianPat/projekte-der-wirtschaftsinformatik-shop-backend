package fhdw.pdw.repository;

import fhdw.pdw.model.Product;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
  public Product findByName(String name);

  public List<Product> findByCategoryId(int id);

  public List<Product> findByCategoryIdIn(Collection<Integer> ids);
}
