package fhdw.pdw.model;

import java.util.List;
import javax.persistence.*;

@Entity(name = "orders")
public class Order extends AbstractEntity {
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  protected List<OrderItem> orderItemList;

  @ManyToOne
  @JoinColumn(name = "user_id")
  protected User user;

  public Order() {}

  public List<OrderItem> getOrderItemList() {
    return orderItemList;
  }

  public void setOrderItemList(List<OrderItem> orderItemList) {
    this.orderItemList = orderItemList;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}