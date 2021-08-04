package fhdw.pdw.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.*;

@Entity
public class ProductVariant extends AbstractEntity {
  protected int stock;
  protected float price;

  @ManyToOne
  @JoinColumn(name = "product_id")
  @JsonIgnore
  protected Product product;

  @ManyToOne
  @JoinColumn(name = "unit_id")
  protected Unit unit;

  @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL)
  @JsonIgnore
  protected List<OrderItem> orderItemList;

  public ProductVariant() {}

  public ProductVariant(int stock, float price) {
    this.stock = stock;
    this.price = price;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Unit getUnit() {
    return unit;
  }

  public void setUnit(Unit unit) {
    this.unit = unit;
  }

  public List<OrderItem> getOrderItemList() {
    return orderItemList;
  }

  public void setOrderItemList(List<OrderItem> orderItemList) {
    this.orderItemList = orderItemList;
  }
}
