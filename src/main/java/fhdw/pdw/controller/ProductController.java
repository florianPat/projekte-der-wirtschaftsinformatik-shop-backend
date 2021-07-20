package fhdw.pdw.controller;

import fhdw.pdw.model.Product;
import fhdw.pdw.repository.ProductRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ProductController {
  protected ProductRepository productRepository;
  Logger logger = LoggerFactory.getLogger(ProductController.class);

  public ProductController(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @GetMapping("/products")
  public List<Product> getProducts(
      @RequestParam(value = "category", required = false) String category,
      @RequestParam(value = "categories", required = false) String categories) {
    if (null != category) {
      return this.productRepository.findByCategoryId(Integer.parseInt(category));
    }

    if (null != categories) {
      ArrayList<Integer> categoryIds = new ArrayList<>();
      Arrays.stream(categories.split(","))
          .mapToInt(Integer::parseInt)
          .forEach(e -> categoryIds.add(e));
      return this.productRepository.findByCategoryIdIn(categoryIds);
    }

    return this.productRepository.findAll();
  }
}
