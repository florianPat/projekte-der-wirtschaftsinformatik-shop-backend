package fhdw.pdw.repository;

import fhdw.pdw.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Integer> {
  /**
   * Sucht einen Behälter mit dem übergebenen Titel, der Menge und der Anzahl an Behältern aus der Datenbank
   */
  Unit findByTitleAndAmountAndNumberOfContainer(String title, float amount, int numberOfContainers);
}
