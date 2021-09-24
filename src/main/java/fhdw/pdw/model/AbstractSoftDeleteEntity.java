package fhdw.pdw.model;

import org.hibernate.annotations.Where;

@Where(clause = "deleted = false")
public abstract class AbstractSoftDeleteEntity extends AbstractEntity {
  protected boolean deleted = false;

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }
}
