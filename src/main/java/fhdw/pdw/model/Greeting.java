package fhdw.pdw.model;

import javax.persistence.*;

@Entity
public class Greeting {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected long id; // TODO UUID and UUIDConverter

  protected String content;

  // @ManyToOne
  // @OneToOne ... same as in doctrine:
  // https://www.youtube.com/watch?v=tSb02fMEB5o&list=PLG5RS5k7TfUpgO0B2SEWwSs6jRwL8QiHw&index=20
  // protected Relation relation;

  protected Greeting() {}

  public Greeting(long id, String content) {
    this.id = id;
    this.content = content;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
