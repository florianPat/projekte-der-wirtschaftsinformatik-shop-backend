package fhdw.pdw.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Category extends AbstractEntity {
  protected String title;
  protected String cover;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
  protected List<Product> products = new ArrayList<>();

  public Category() {}

  public Category(int id, String title, String cover) {
    this.id = id;
    this.title = title;
    this.cover = cover;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
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
