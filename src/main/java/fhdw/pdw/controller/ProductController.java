package fhdw.pdw.controller;

import fhdw.pdw.model.Product;
import fhdw.pdw.repository.ProductRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequestMapping("/api")
@RestController
public class ProductController {
  protected ProductRepository productRepository;

  public ProductController(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @GetMapping("/products")
  public List<Product> getProducts() {
    return this.productRepository.findAll();
  }
}
