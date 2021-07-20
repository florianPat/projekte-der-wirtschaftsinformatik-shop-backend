package fhdw.pdw.repository;

import fhdw.pdw.model.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProducerRepository extends JpaRepository<Producer, Integer> {
  public Producer findByName(String name);
}
