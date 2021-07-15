package db.migration;

import fhdw.pdw.SpringUtility;
import fhdw.pdw.model.Product;
import fhdw.pdw.repository.ProductRepository;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V2_3_1__evian_water extends BaseJavaMigration {
  protected ProductRepository productRepository = SpringUtility.getBean(ProductRepository.class);

  @Override
  public void migrate(Context context) throws Exception {
    Product product = new Product("Evian Water");
    productRepository.save(product);
  }
}
