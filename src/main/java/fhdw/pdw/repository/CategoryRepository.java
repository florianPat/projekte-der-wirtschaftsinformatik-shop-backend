package fhdw.pdw.repository;

import fhdw.pdw.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
  /**
   * Gibt alle Kategorienm, welche dem Titel entsprechen, aus der Datenbank zurück
   */
  public Category findByTitle(String title);
}
