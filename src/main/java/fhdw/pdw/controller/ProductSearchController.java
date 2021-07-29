package fhdw.pdw.controller;

import fhdw.pdw.model.Product;
import fhdw.pdw.repository.ProductRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ProductSearchController {
  protected ProductRepository productRepository;

  public ProductSearchController(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @GetMapping("/productSearch")
  public List<Product> findProducts(@RequestParam(value = "searchTerm") String searchTerm) {
    int searchId = 0;
    try {
      searchId = Integer.parseInt(searchTerm);
    } catch (NumberFormatException ignored) {
    }
    return productRepository
        .findByNameIgnoreCaseContainingOrIdOrProducerIgnoreCaseContainingOrCategoryTitleIgnoreCaseContaining(
            searchTerm, searchId, searchTerm, searchTerm);
  }
}
