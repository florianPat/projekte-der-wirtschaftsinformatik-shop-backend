package fhdw.pdw.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id",
    scope = Product.class)
public class Product extends AbstractEntity {
  protected String name;
  protected String cover;

  @ManyToOne
  @JoinColumn(name = "category_id")
  protected Category category;

  // @ManyToOne
  // @JoinColumn(name = "producer_id")
  protected String producer;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  protected List<ProductVariant> variants = new ArrayList<>();

  // @ManyToMany(mappedBy = "products")
  protected String allergens;

  protected int minAge;

  public Product() {}

  public Product(String name, String cover, int minAge) {
    this.name = name;
    this.cover = cover;
    this.minAge = minAge;
  }

  public Product(String name, String cover, Category category, String producer, int minAge) {
    this.name = name;
    this.cover = cover;
    this.category = category;
    this.producer = producer;
    this.minAge = minAge;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getProducer() {
    return producer;
  }

  public void setProducer(String producer) {
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

  public String getAllergens() {
    return allergens;
  }

  public void setAllergens(String allergens) {
    this.allergens = allergens;
  }

  public int getMinAge() {
    return minAge;
  }

  public void setMinAge(int minAge) {
    this.minAge = minAge;
  }
}
