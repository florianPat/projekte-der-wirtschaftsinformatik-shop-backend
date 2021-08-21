package fhdw.pdw.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;

@Entity
public class Role extends AbstractEntity {
  @Enumerated(EnumType.STRING)
  protected RoleName name;

  @ManyToMany(mappedBy = "roles")
  protected List<User> users;

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

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }
}
