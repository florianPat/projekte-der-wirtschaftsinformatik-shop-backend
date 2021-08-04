package fhdw.pdw.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrderItem extends AbstractEntity {
  @ManyToOne
  @JoinColumn(name = "order_id")
  @JsonIgnore
  protected Order order;

  @ManyToOne
  @JoinColumn(name = "productVariant_id")
  protected ProductVariant productVariant;

  protected int quantity;

  public OrderItem() {}

  public OrderItem(int quantity) {
    this.quantity = quantity;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public ProductVariant getProductVariant() {
    return productVariant;
  }

  public void setProductVariant(ProductVariant productVariant) {
    this.productVariant = productVariant;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
