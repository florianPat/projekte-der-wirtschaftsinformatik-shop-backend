package fhdw.pdw.repository;

import fhdw.pdw.model.AbstractEntity;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SoftDeleteRepository<T extends AbstractEntity, ID extends Serializable>
    extends JpaRepository<T, ID> {
  public void softDeleteById(ID id);

  public void softDelete(T entity);

  public void softDeleteAllById(Iterable<? extends ID> ids);

  public void softDeleteAllByIdInBatch(Iterable<ID> ids);

  public void softDeleteAll(Iterable<? extends T> entities);

  public void softDeleteAllInBatch(Iterable<T> entities);

  public void softDeleteAll();

  public void softDeleteAllInBatch();
}
