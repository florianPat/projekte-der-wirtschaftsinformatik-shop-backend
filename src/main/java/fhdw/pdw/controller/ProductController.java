package fhdw.pdw.controller;

import fhdw.pdw.model.Product;
import fhdw.pdw.repository.ProductRepository;
import java.util.*;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class ProductController {
  protected ProductRepository productRepository;

  public ProductController(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  /**
   * API-Endpunkt, um alle Produkte zu erhalten. Des Weiteren kann nach Kategorien gefiltert werden
   */
  @GetMapping("/products")
  public List<Product> getProducts(
      @RequestParam(value = "category", required = false) Integer category,
      @RequestParam(value = "categories", required = false) Collection<Integer> categories) {
    if (null != category) {
      return this.productRepository.findByCategoryId(category);
    }

    if (null != categories) {
      return this.productRepository.findByCategoryIdIn(categories);
    }

    return this.productRepository.findAll();
  }

  /**
   * API-Endpunkt, um ein bestimmtes Produkt zu erhalten
   */
  @GetMapping("/products/{id}")
  public Product getProduct(@PathVariable int id) {
    Optional<Product> result = this.productRepository.findById(id);
    return result.get();
  }
}
