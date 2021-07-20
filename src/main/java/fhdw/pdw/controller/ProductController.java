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

  @GetMapping("/products/{id}")
  public Product getProduct(@PathVariable int id) {
    Optional<Product> result = this.productRepository.findById(id);
    return result.get();
  }
}
