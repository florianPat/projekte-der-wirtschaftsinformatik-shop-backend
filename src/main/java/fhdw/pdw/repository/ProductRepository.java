package fhdw.pdw.repository;

import fhdw.pdw.model.Product;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    /**
     * Sucht ein Produkt mit dem gegebenen Namen aus der Datenbank
     */
    public Product findByName(String name);

    /**
     * Sucht alle Produkte einer Kategorie aus der Datenbank
     */
    public List<Product> findByCategoryId(int id);

    /**
     * Sucht alle Produkte aus einer Menge von Kategorien aus der Datenbank
     */
    public List<Product> findByCategoryIdIn(Collection<Integer> ids);

    /**
     * Sucht alle Produkte, welche den übergebenen Namen, die Id, den Hersteller und der Kategoie entsprechen
     */
    public List<Product>
        findByNameIgnoreCaseContainingOrIdOrProducerIgnoreCaseContainingOrCategoryTitleIgnoreCaseContaining(
            String name, int id, String producer, String categoryTitle);
}
