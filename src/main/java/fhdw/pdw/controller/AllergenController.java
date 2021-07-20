package fhdw.pdw.controller;

import fhdw.pdw.model.Allergen;
import fhdw.pdw.repository.AllergenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequestMapping("/api")
@RestController
public class AllergenController {

  protected AllergenRepository allergenRepository;

  public AllergenController(AllergenRepository allergenRepository) {
    this.allergenRepository = allergenRepository;
  }

  @GetMapping("/allergens")
  public List<Allergen> getAllergens() {
    return this.allergenRepository.findAll();
  }
}
