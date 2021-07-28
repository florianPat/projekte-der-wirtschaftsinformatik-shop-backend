package fhdw.pdw.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Role extends AbstractEntity {
  @Enumerated(EnumType.STRING)
  protected RoleName name;

  public Role() {}

  public Role(RoleName name) {
    this.name = name;
  }

  public RoleName getName() {
    return name;
  }

  public void setName(RoleName name) {
    this.name = name;
  }
}
