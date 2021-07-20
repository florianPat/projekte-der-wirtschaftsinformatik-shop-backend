package fhdw.pdw.repository;

import fhdw.pdw.model.Allergen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllergenRepository extends JpaRepository<Allergen, Integer> {}
