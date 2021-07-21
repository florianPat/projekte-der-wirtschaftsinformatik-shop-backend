package db.migration;

import fhdw.pdw.SpringUtility;
import fhdw.pdw.model.Product;
import fhdw.pdw.model.ProductVariant;
import fhdw.pdw.model.Unit;
import fhdw.pdw.repository.*;
import org.flywaydb.core.api.migration.Context;

public class R__products extends AbstractMigration {
  protected ProductRepository productRepository = SpringUtility.getBean(ProductRepository.class);
  protected CategoryRepository categoryRepository = SpringUtility.getBean(CategoryRepository.class);
  protected UnitRepository unitRepository = SpringUtility.getBean(UnitRepository.class);
  protected ProductVariantRepository productVariantRepository =
      SpringUtility.getBean(ProductVariantRepository.class);

  @Override
  public void migrate(Context context) {
    Product product = productRepository.findByName("Evian Wasser");
    if (product == null) {
      product = new Product();
    }

    product.setName("Evian Wasser");
    product.setCover(
        "https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcR9BpV8FtkZ0jy4JLt7v14VlNI3Eauu1_QwC4YUaUSBZx0bCXucNh_KlX1iOrr0NQNEzsvuTW82UUY&usqp=CAc");
    product.setProducer("Evian");
    product.setCategory(categoryRepository.findByTitle("Wasser"));
    productRepository.save(product);

    Unit unit = this.getOrCreateUnit("L", 0.5f, 6);

    ProductVariant productVariant = new ProductVariant(500, 6.5f);
    productVariant.setUnit(unit);
    productVariant.setProduct(product);
    productVariantRepository.save(productVariant);
  }

  protected Unit getOrCreateUnit(String title, float amount, int numberOfContainer) {
    Unit unit =
        unitRepository.findByTitleAndAmountAndNumberOfContainer(title, amount, numberOfContainer);
    if (null == unit) {
      unit = new Unit(title, amount, numberOfContainer);
      unitRepository.save(unit);
    }
    return unit;
  }
}
