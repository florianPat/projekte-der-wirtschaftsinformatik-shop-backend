package fhdw.pdw.repository;

import fhdw.pdw.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository
    extends JpaRepository<Unit, Integer>, SoftDeleteRepository<Unit, Integer> {
  Unit findByTitleAndAmountAndNumberOfContainer(String title, float amount, int numberOfContainers);
}
