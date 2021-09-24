package fhdw.pdw;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fhdw.pdw.constraints.validators.UniqueUserEmailValidator;
import fhdw.pdw.model.User;
import fhdw.pdw.repository.UserRepository;
import java.util.List;
import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

public class UniqueUserEmailValidatorTest extends AbstractFunctionalTestCase {
  @MockBean protected UserRepository userRepository;

  protected UniqueUserEmailValidator uniqueUserEmailValidator;

  @BeforeEach
  public void beforeEach() {
    uniqueUserEmailValidator = new UniqueUserEmailValidator();
  }

  protected User createUser(int id) {
    User result =
        new User(
            "Flo",
            "Flo",
            "Nordstr",
            "33564",
            "Nordstadt",
            "15.08.1999",
            "flo@test.de",
            "password",
            true,
            true);
    result.setId(id);
    return result;
  }

  @Test
  public void testUniqueEmailValidatorFailsOnExistingEmail() {
    User userInRepo = createUser(1);
    Mockito.when(userRepository.findAllByEmailIgnoreCase(userInRepo.getEmail()))
        .thenReturn(List.of(userInRepo));
    uniqueUserEmailValidator.setUserRepository(userRepository);

    boolean isValid =
        uniqueUserEmailValidator.isValid(
            createUser(2), Mockito.mock(ConstraintValidatorContext.class));

    Mockito.verify(userRepository, Mockito.times(1))
        .findAllByEmailIgnoreCase(userInRepo.getEmail());
    assertFalse(isValid);
  }

  @Test
  public void testUniqueEmailValidatorSucceedsOnSameUser() {
    User userInRepo = createUser(1);
    Mockito.when(userRepository.findAllByEmailIgnoreCase(userInRepo.getEmail()))
        .thenReturn(List.of(userInRepo));
    uniqueUserEmailValidator.setUserRepository(userRepository);

    boolean isValid =
        uniqueUserEmailValidator.isValid(
            userInRepo, Mockito.mock(ConstraintValidatorContext.class));

    Mockito.verify(userRepository, Mockito.times(1))
        .findAllByEmailIgnoreCase(userInRepo.getEmail());
    assertTrue(isValid);
  }

  @Test
  public void testUniqueEmailValidatorSucceedsOnNoOtherUsers() {
    User user = createUser(1);
    Mockito.when(userRepository.findAllByEmailIgnoreCase(user.getEmail())).thenReturn(List.of());
    uniqueUserEmailValidator.setUserRepository(userRepository);

    boolean isValid =
        uniqueUserEmailValidator.isValid(user, Mockito.mock(ConstraintValidatorContext.class));

    Mockito.verify(userRepository, Mockito.times(1)).findAllByEmailIgnoreCase(user.getEmail());
    assertTrue(isValid);
  }
}
