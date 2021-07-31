package fhdw.pdw;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.*;

import fhdw.pdw.model.User;
import fhdw.pdw.model.dto.ApiResponse;
import fhdw.pdw.repository.UserRepository;
import java.util.stream.Stream;
import javax.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest extends AbstractHttpRequestTest {

  @Autowired protected UserRepository userRepository;

  @Test
  public void registrationShouldSucceed() {
    User user = createUser();

    ResponseEntity<ApiResponse> response = postRegisterRequestWith(user);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertThat(response.getBody()).hasFieldOrPropertyWithValue("success", true);

    MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
    assertThatNoException()
        .isThrownBy(
            () -> {
              assertEquals(user.getEmail(), receivedMessage.getAllRecipients()[0].toString());
              assertEquals("siphydrated@gmail.com", receivedMessage.getFrom()[0].toString());
            });

    assertNotNull(userRepository.findByEmail(user.getEmail()));
  }

  @Test
  public void registrationShouldNotSucceedWithSameEmail() {
    User user = createUser();

    ResponseEntity<ApiResponse> response = postRegisterRequestWith(user);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    response = postRegisterRequestWith(user);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertThat(response.getBody()).hasFieldOrPropertyWithValue("success", false);
  }

  static Stream<User> getInvalidUsers() {
    User user1 = createUser();
    user1.setEmail("IchBinKeineEmail");
    User user2 = createUser();
    user2.setPasswordRepeat("IchBinNichtDasGleichePassword");
    User user3 = createUser();
    user3.setPrivacyStatement(false);
    User user4 = createUser();
    user4.setCity("");

    return Stream.of(user1, user2, user3, user4);
  }

  @ParameterizedTest
  @MethodSource("fhdw.pdw.AuthControllerTest#getInvalidUsers")
  public void invalidUserCanNotRegister(User user) {
    ResponseEntity<ApiResponse> response = postRegisterRequestWith(user);
    assertTrue(
        HttpStatus.BAD_REQUEST == response.getStatusCode()
            || HttpStatus.INTERNAL_SERVER_ERROR == response.getStatusCode());
  }

  protected static User createUser() {
    User user = new User();
    user.setFirstName("Flo");
    user.setLastName("Pat");
    user.setStreet("West");
    user.setZip("77777");
    user.setCity("Spring City");
    user.setBirthday("15.08.1999");
    user.setEmail("tester@test.de");
    user.setPassword("HalloDuIchBinEs12$");
    user.setPasswordRepeat("HalloDuIchBinEs12$");
    user.setPrivacyStatement(true);

    return user;
  }

  protected ResponseEntity<ApiResponse> postRegisterRequestWith(User user) {
    return restTemplate.postForEntity(
        "http://localhost:" + port + "/api/auth/register", user, ApiResponse.class);
  }
}
