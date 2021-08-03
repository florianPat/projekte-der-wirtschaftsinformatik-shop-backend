package fhdw.pdw.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity(name = "orders")
public class Order extends AbstractEntity {
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  protected List<OrderItem> orderItemList;

  public Order() {}

  public List<OrderItem> getOrderItemList() {
    return orderItemList;
  }

  public void setOrderItemList(List<OrderItem> orderItemList) {
    this.orderItemList = orderItemList;
  }
}
