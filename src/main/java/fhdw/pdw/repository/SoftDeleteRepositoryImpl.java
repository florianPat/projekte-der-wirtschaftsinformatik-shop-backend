package fhdw.pdw.repository;

import static org.springframework.data.jpa.repository.query.QueryUtils.*;

import fhdw.pdw.model.AbstractEntity;
import fhdw.pdw.model.AbstractSoftDeleteEntity;
import java.io.Serializable;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.metamodel.SingularAttribute;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.util.ProxyUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

public class SoftDeleteRepositoryImpl<T extends AbstractEntity, ID extends Serializable>
    extends SimpleJpaRepository<T, ID> implements SoftDeleteRepository<T, ID> {
  protected static final String SOFT_DELETE_ALL_QUERY_BY_ID_STRING =
      "update %s set deleted = 1 where %s in :ids";
  public static final String SOFT_DELETE_ALL_QUERY_STRING = "update %s set deleted = 1";

  protected static final String ID_MUST_NOT_BE_NULL = "The given id must not be null!";
  protected JpaEntityInformation<T, ?> entityInformation;
  protected EntityManager em;

  public SoftDeleteRepositoryImpl(
      JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
    super(entityInformation, entityManager);

    this.em = entityManager;
    this.entityInformation = entityInformation;
  }

  public SoftDeleteRepositoryImpl(Class<T> domainClass, EntityManager em) {
    this(JpaEntityInformationSupport.getEntityInformation(domainClass, em), em);
  }

  protected boolean isSoftDeleteEntity() {
    return AbstractSoftDeleteEntity.class.isAssignableFrom(entityInformation.getJavaType());
  }

  @Transactional
  @Override
  public void softDeleteById(ID id) {

    Assert.notNull(id, ID_MUST_NOT_BE_NULL);

    softDelete(
        findById(id)
            .orElseThrow(
                () ->
                    new EmptyResultDataAccessException(
                        String.format(
                            "No %s entity with id %s exists!", entityInformation.getJavaType(), id),
                        1)));
  }

  @Override
  @Transactional
  public void softDelete(T entity) {
    Assert.notNull(entity, "Entity must not be null!");

    if (entityInformation.isNew(entity)) {
      return;
    }

    Class<?> type = ProxyUtils.getUserClass(entity);

    T existing = (T) em.find(type, entityInformation.getId(entity));

    // if the entity to be deleted doesn't exist, delete is a NOOP
    if (existing == null) {
      return;
    }

    if (!(existing instanceof AbstractSoftDeleteEntity)) {
      throw new RuntimeException(
          "To soft delete an entity, it needs to be an AbstractSoftDeletedEntity");
    }
    AbstractSoftDeleteEntity softDeleteEntity = (AbstractSoftDeleteEntity) existing;

    softDeleteEntity.setDeleted(true);
    saveAndFlush(existing);
  }

  @Override
  @Transactional
  public void softDeleteAllById(Iterable<? extends ID> ids) {

    Assert.notNull(ids, "Ids must not be null!");

    for (ID id : ids) {
      softDeleteById(id);
    }
  }

  @Override
  @Transactional
  public void softDeleteAllByIdInBatch(Iterable<ID> ids) {

    Assert.notNull(ids, "Ids must not be null!");

    if (!ids.iterator().hasNext()) {
      return;
    }

    SingularAttribute<? super T, ?> attribute = entityInformation.getIdAttribute();
    if (attribute == null) {
      throw new RuntimeException("attribute was null!");
    }

    String queryString =
        String.format(
            SOFT_DELETE_ALL_QUERY_BY_ID_STRING,
            entityInformation.getEntityName(),
            attribute.getName());

    Query query = em.createQuery(queryString);
    query.setParameter("ids", ids);

    query.executeUpdate();
  }

  @Override
  @Transactional
  public void softDeleteAll(Iterable<? extends T> entities) {

    Assert.notNull(entities, "Entities must not be null!");

    for (T entity : entities) {
      softDelete(entity);
    }
  }

  @Override
  @Transactional
  public void softDeleteAllInBatch(Iterable<T> entities) {

    Assert.notNull(entities, "Entities must not be null!");

    if (!entities.iterator().hasNext()) {
      return;
    }

    applyAndBind(
            getQueryString(SOFT_DELETE_ALL_QUERY_STRING, entityInformation.getEntityName()),
            entities,
            em)
        .executeUpdate();
  }

  @Override
  @Transactional
  public void softDeleteAll() {
    for (T element : findAll()) {
      softDelete(element);
    }
  }

  @Override
  @Transactional
  public void softDeleteAllInBatch() {
    em.createQuery(getQueryString(SOFT_DELETE_ALL_QUERY_STRING, entityInformation.getEntityName()))
        .executeUpdate();
  }

  @Override
  public <S extends T> S save(S entity) {
    if (isSoftDeleteEntity()) {
      if (!(entity instanceof AbstractSoftDeleteEntity)) {
        throw new RuntimeException("The entity needs to be an AbstractSoftDeletedEntity!!");
      }

      Assert.notNull(entity, "Entity must not be null.");

      if (entityInformation.isNew(entity)) {
        em.persist(entity);
        return entity;
      } else {
        AbstractSoftDeleteEntity softDeleteEntity = (AbstractSoftDeleteEntity) entity;
        S clonedEntity = (S) softDeleteEntity.clone();
        clonedEntity.setId(0);
        em.persist(clonedEntity);

        softDeleteEntity.setDeleted(true);
        return em.merge(entity);
      }
    } else {
      return super.save(entity);
    }
  }

  @Override
  public Optional<T> findById(ID id) {
    Optional<T> result = super.findById(id);
    return result;
  }

  @Override
  public T getById(ID id) {
    T result = super.getById(id);
    return result;
  }

  @Override
  public boolean existsById(ID id) {
    boolean result = super.existsById(id);
    return result;
  }
}
