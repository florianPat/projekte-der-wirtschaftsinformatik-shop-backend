package fhdw.pdw.repository;

import fhdw.pdw.model.Unit;
import org.springframework.data.repository.CrudRepository;

public interface UnitRepository extends CrudRepository<Unit, Integer> {
  Unit findByTitleAndAmountAndNumberOfContainer(String title, float amount, int numberOfContainers);
}
