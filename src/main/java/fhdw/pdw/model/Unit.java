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
    scope = Unit.class)
public class Unit extends AbstractEntity {
  protected String title;
  protected float amount;
  protected int numberOfContainer;

  @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
  protected List<ProductVariant> variants = new ArrayList<>();

  public Unit() {}

  public Unit(String title, float amount, int numberOfContainer) {
    this.title = title;
    this.amount = amount;
    this.numberOfContainer = numberOfContainer;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public float getAmount() {
    return amount;
  }

  public void setAmount(float amount) {
    this.amount = amount;
  }

  public int getNumberOfContainer() {
    return numberOfContainer;
  }

  public void setNumberOfContainer(int numberOfContainer) {
    this.numberOfContainer = numberOfContainer;
  }
}
