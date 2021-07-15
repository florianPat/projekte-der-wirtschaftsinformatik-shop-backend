package fhdw.pdw.controller;

import fhdw.pdw.model.Category;
import fhdw.pdw.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class CategoryController {
  protected CategoryRepository categoryRepository;

  @Autowired
  public CategoryController(CategoryRepository categpryRepository) {
    this.categoryRepository = categpryRepository;
  }

  @GetMapping("/categories")
  public Iterable<Category> getCategories() {
    return this.categoryRepository.findAll();
  }
}
