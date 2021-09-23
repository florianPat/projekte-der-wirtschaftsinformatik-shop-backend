package fhdw.pdw.mapper;

import fhdw.pdw.model.Category;
import fhdw.pdw.model.Product;
import fhdw.pdw.model.ProductVariant;
import fhdw.pdw.model.Unit;
import fhdw.pdw.model.dto.ProductDto;
import fhdw.pdw.repository.CategoryRepository;
import fhdw.pdw.repository.ProductRepository;
import fhdw.pdw.repository.ProductVariantRepository;
import fhdw.pdw.repository.UnitRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
  protected ProductRepository productRepository;
  protected CategoryRepository categoryRepository;
  protected UnitRepository unitRepository;
  protected ProductVariantRepository productVariantRepository;

  public ProductMapper(
      ProductRepository productRepository,
      CategoryRepository categoryRepository,
      UnitRepository unitRepository,
      ProductVariantRepository productVariantRepository) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
    this.unitRepository = unitRepository;
    this.productVariantRepository = productVariantRepository;
  }

  /**
   * Um für die gleiche Produkt Entität, welche mit verschiedenen Varianten (Anzahl an Kisten oder
   * andere Preise) befüllt werden soll, auch nur eine Zeile in der Datenbank zu benutzten wird
   * ein Hash generiert, womit überprüft werden kann, ob das Produkt schon vorhanden ist und die
   * Variante diesem Produkt hinzugefügt werden kann
   */
  protected String getNormalizedHash(ProductDto productDto) {
    return productDto.getName()
        + productDto.getMinAge()
        + productDto.getProducer()
        + productDto.getAllergens()
        + productDto.getCategory();
  }

  /**
   * Überführt die Daten einer CSV Datei in die Produkt Entitäten
   */
  public List<Product> mapFrom(List<ProductDto> dtos) {
    HashMap<String, Product> normalizedResultMap = new HashMap<>();

    for (ProductDto dto : dtos) {
      Product product = new Product(dto.getName(), dto.getCover(), dto.getMinAge());
      product.setProducer(dto.getProducer());
      product.setAllergens(dto.getAllergens());
      product.setCategory(getOrCreateCategory(dto.getCategory()));

      List<ProductVariant> variants = new ArrayList<>();

      Unit unit =
          this.getOrCreateUnit(dto.getUnitTitle(), dto.getAmount(), dto.getNumberOfContainer());
      ProductVariant productVariant = new ProductVariant(500, dto.getPrice());
      productVariant.setUnit(unit);
      productVariant.setProduct(product);
      variants.add(productVariant);

      product.setVariants(variants);

      if (normalizedResultMap.containsKey(getNormalizedHash(dto))) {
        Product alreadyMappedProduct = normalizedResultMap.get(getNormalizedHash(dto));
        alreadyMappedProduct.getVariants().addAll(product.getVariants());
      } else {
        normalizedResultMap.put(getNormalizedHash(dto), product);
      }
    }

    return new ArrayList<>(normalizedResultMap.values());
  }

  /**
   * Speichert die Daten aus der CSV Datei in der Datenbank ab
   */
  public void mapAndReplaceFrom(List<ProductDto> dtos) {
    productRepository.deleteAll();
    unitRepository.deleteAll();
    productVariantRepository.deleteAll();
    List<Product> products = mapFrom(dtos);
    for (Product product : products) {
      if (0 == product.getCategory().getId()) {
        Category category = categoryRepository.findByTitle(product.getCategory().getTitle());
        if (null == category) {
          category = categoryRepository.save(product.getCategory());
        }
        product.setCategory(category);
      }

      List<ProductVariant> productVariants = product.getVariants();
      product.setVariants(null);
      Product persistedProduct = productRepository.save(product);

      for (ProductVariant variant : productVariants) {
        variant.setProduct(persistedProduct);
        if (0 == variant.getUnit().getId()) {
          Unit unit =
              unitRepository.findByTitleAndAmountAndNumberOfContainer(
                  variant.getUnit().getTitle(),
                  variant.getUnit().getAmount(),
                  variant.getUnit().getNumberOfContainer());
          if (null == unit) {
            unit = unitRepository.save(variant.getUnit());
          }
          variant.setUnit(unit);
        }
        productVariantRepository.save(variant);
      }
    }
  }

  /**
   * Um nur eine Unit für die gleichen Daten zu erzeugen, wird vor der Erstellung geprüft, ob ein
   * Objekt mit gleichen Daten bereits existiert und im Falle diese genutzt
   */
  protected Unit getOrCreateUnit(String title, float amount, int numberOfContainer) {
    Unit unit =
        unitRepository.findByTitleAndAmountAndNumberOfContainer(title, amount, numberOfContainer);
    if (null == unit) {
      unit = new Unit(title, amount, numberOfContainer);
    }
    return unit;
  }

  /**
   * Um nur eine Kategorie für die gleichen Daten zu erzeugen, wird vor der Erstellung geprüft, ob
   * ein Objekt mit den gleichen Daten bereits existiert und im Falle dieses genutzt
   */
  protected Category getOrCreateCategory(String title) {
    Category category = categoryRepository.findByTitle(title);
    if (null == category) {
      category = new Category(title, "");
    }
    return category;
  }
}
