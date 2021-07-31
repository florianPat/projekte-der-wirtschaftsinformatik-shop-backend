package fhdw.pdw;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fhdw.pdw.csvimport.ProductMapper;
import fhdw.pdw.model.Product;
import fhdw.pdw.model.Unit;
import fhdw.pdw.model.dto.ProductDto;
import fhdw.pdw.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductMapperTest extends AbstractFunctionalTestCase {
  @Autowired protected ProductMapper productMapper;

  @Autowired protected ProductRepository productRepository;

  @Test
  public void testProductMapper() {
    List<ProductDto> dtos = new ArrayList<>();
    dtos.add(
        new ProductDto(
            "Evain Wasser", "Wasser", "Evian", "6 x 0.5 L", 12.5f, "12, 8", "https://ein-link"));
    List<Product> products = productMapper.mapFrom(dtos);
    assertEquals(1, products.size());
    Product product = products.get(0);
    assertEquals("Evain Wasser", product.getName());
    assertEquals("Evian", product.getProducer());
    assertEquals("Wasser", product.getCategory().getTitle());
    assertEquals(1, product.getVariants().size());
    Unit unit = product.getVariants().get(0).getUnit();
    assertEquals(6, unit.getNumberOfContainer());
    assertEquals(0.5f, unit.getAmount());
    assertEquals("L", unit.getTitle());
    assertEquals(12.5f, product.getVariants().get(0).getPrice());
    assertEquals("12, 8", product.getAllergens());
    assertEquals("https://ein-link", product.getCover());

    productMapper.mapAndReplaceFrom(dtos);
    assertEquals(1, productRepository.findAll().size());
    assertEquals("Evain Wasser", productRepository.findAll().get(0).getName());
  }
}
