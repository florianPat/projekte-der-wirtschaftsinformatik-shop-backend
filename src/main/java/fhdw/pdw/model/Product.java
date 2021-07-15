package fhdw.pdw.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class Product extends AbstractEntity {
  protected String name;

  @ManyToOne
  @JoinColumn(name = "category_id")
  protected Category category;

  @ManyToOne
  @JoinColumn(name = "producer_id")
  protected Producer producer;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  protected List<ProductVariant> variants = new ArrayList<>();

  @ManyToMany(mappedBy = "products")
  protected List<Allergen> allergens = new ArrayList<>();

  public Product(String name) {
    this.name = name;
  }

  public Product(String name, Category category, Producer producer) {
    this.name = name;
    this.category = category;
    this.producer = producer;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public Producer getProducer() {
    return producer;
  }

  public void setProducer(Producer producer) {
    this.producer = producer;
  }

  public List<ProductVariant> getVariants() {
    return variants;
  }

  public void setVariants(List<ProductVariant> variants) {
    this.variants = variants;
  }

  public void addVariant(ProductVariant variant) {
    this.variants.add(variant);
  }

  public void removeVariant(ProductVariant variant) {
    this.variants.remove(variant);
  }

  public List<Allergen> getAllergens() {
    return allergens;
  }

  public void setAllergens(List<Allergen> allergens) {
    this.allergens = allergens;
  }

  public void addAllergen(Allergen allergen) {
    this.allergens.add(allergen);
  }

  public void removeAllergen(Allergen allergen) {
    this.allergens.remove(allergen);
  }
}
