package fhdw.pdw;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fhdw.pdw.mapper.OrderMapper;
import fhdw.pdw.model.*;
import fhdw.pdw.model.dto.OrderItemDto;
import fhdw.pdw.repository.CategoryRepository;
import fhdw.pdw.repository.ProductRepository;
import fhdw.pdw.repository.UnitRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderMapperTest extends AbstractFunctionalTestCase {
  @Autowired protected ProductRepository productRepository;

  @Autowired protected CategoryRepository categoryRepository;

  @Autowired protected UnitRepository unitRepository;

  @Autowired protected OrderMapper orderMapper;

  protected static final int QUANTITY = 5;
  protected static final String PRODUCT_NAME = "Import Wasser";
  protected static final String CATEGORY_NAME = "Wasser";
  protected static final int NUMBER_OF_CONTAINER = 3;

  @Test
  public void testOrderMapper() {
    Product productReference = setupProductFixtures();

    ArrayList<OrderItemDto> orderItemDtos = new ArrayList<>();
    orderItemDtos.add(new OrderItemDto(QUANTITY, productReference.getVariants().get(0).getId()));
    Order order = orderMapper.mapFrom(orderItemDtos);

    assertEquals(1, order.getOrderItemList().size());
    OrderItem orderItem = order.getOrderItemList().get(0);
    assertEquals(QUANTITY, orderItem.getQuantity());
    assertEquals(
        productReference.getVariants().get(0).getId(), orderItem.getProductVariant().getId());
    assertEquals(PRODUCT_NAME, orderItem.getProductVariant().getProduct().getName());
    assertEquals(
        CATEGORY_NAME, orderItem.getProductVariant().getProduct().getCategory().getTitle());
    assertEquals(
        NUMBER_OF_CONTAINER, orderItem.getProductVariant().getUnit().getNumberOfContainer());
  }

  protected Product setupProductFixtures() {
    Category category = categoryRepository.findByTitle(CATEGORY_NAME);
    Product product =
        new Product(PRODUCT_NAME, "https://cover-wasser", category, "Wasser Producer", 0);
    product.setAllergens("1, 2");
    List<ProductVariant> variants = new ArrayList<>();
    Unit unit = new Unit("L", 5.5f, 3);
    unit = unitRepository.save(unit);
    ProductVariant variant = new ProductVariant(500, 12.5f);
    variant.setUnit(unit);
    variant.setProduct(product);
    variants.add(variant);
    product.setVariants(variants);
    return productRepository.save(product);
  }
}
