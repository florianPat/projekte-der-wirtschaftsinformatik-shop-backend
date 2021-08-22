package fhdw.pdw.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity(name = "orders")
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id",
    scope = Order.class)
public class Order extends AbstractEntity {
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  protected List<OrderItem> orderItemList;

  protected OrderStatus status = OrderStatus.ORDERED;

  @ManyToOne
  @JoinColumn(name = "user_id")
  protected User user;

  protected Date createdAt = new Date();

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

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public Date getCreatedAt() {
    return new Date(createdAt.getTime());
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = new Date(createdAt.getTime());
  }
}
