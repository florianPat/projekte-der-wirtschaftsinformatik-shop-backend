package fhdw.pdw.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;
import javax.persistence.*;

@Entity
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id",
    scope = Allergen.class)
public class Allergen extends AbstractEntity {
  protected String name;

  @ManyToMany
  @JoinTable(
      name = "allergen_products",
      joinColumns = @JoinColumn(name = "allergen_id"),
      inverseJoinColumns = @JoinColumn(name = "product_id"))
  protected List<Product> products;

  public Allergen() {}

  public Allergen(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

  public void addProduct(Product product) {
    this.products.add(product);
  }

  public void removeProduct(Product product) {
    this.products.remove(product);
  }
}
