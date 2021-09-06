package fhdw.pdw.constraints.validators;

import fhdw.pdw.SpringUtility;
import fhdw.pdw.constraints.UniqueUserEmail;
import fhdw.pdw.model.User;
import fhdw.pdw.repository.UserRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

public class UniqueUserEmailValidator implements ConstraintValidator<UniqueUserEmail, User> {
  protected EntityManagerFactory entityManagerFactory =
      SpringUtility.getBean(EntityManagerFactory.class);
  protected UserRepository userRepository;

  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void initialize(UniqueUserEmail constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    JpaRepositoryFactory jpaRepositoryFactory = new JpaRepositoryFactory(entityManager);
    userRepository = jpaRepositoryFactory.getRepository(UserRepository.class);
  }

  @Override
  public boolean isValid(User value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    if (value.getEmail() == null || value.getEmail().isBlank()) {
      return true;
    }

    String stringValue = value.getEmail();
    List<User> users = userRepository.findAllByEmailIgnoreCase(stringValue);

    return users.isEmpty() || (users.size() == 1 && users.get(0).getId() == value.getId());
  }
}
