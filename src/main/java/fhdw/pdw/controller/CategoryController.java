package fhdw.pdw.controller;

import fhdw.pdw.model.Category;
import fhdw.pdw.repository.CategoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class CategoryController {
  protected CategoryRepository categpryRepository;

  @Autowired
  public CategoryController(CategoryRepository categpryRepository) {
    this.categpryRepository = categpryRepository;
  }

  @GetMapping("/categories")
  public List<Category> getCategories() {
    return (List<Category>) this.categpryRepository.findAll();
  }
}
