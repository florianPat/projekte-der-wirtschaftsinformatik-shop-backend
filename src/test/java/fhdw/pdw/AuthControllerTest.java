package fhdw.pdw;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.*;

import fhdw.pdw.model.User;
import fhdw.pdw.model.dto.ApiResponse;
import fhdw.pdw.model.dto.JwtAuthenticationResponse;
import fhdw.pdw.model.dto.LoginUser;
import fhdw.pdw.repository.UserRepository;
import java.util.Optional;
import java.util.stream.Stream;
import javax.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;

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

  @Test
  public void loginShouldSucceedWithValidCredentials() {
    User user = createUser();

    ResponseEntity<ApiResponse> registerResponse = postRegisterRequestWith(user);
    assertEquals(HttpStatus.CREATED, registerResponse.getStatusCode());

    ResponseEntity<JwtAuthenticationResponse> loginResponse =
        postLoginRequestWith(new LoginUser(user.getEmail(), user.getPassword()));
    assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
    JwtAuthenticationResponse loginResponseBody = loginResponse.getBody();
    assertNotNull(loginResponseBody);
    assertThat(loginResponseBody).hasFieldOrProperty("token");
    assertThat(loginResponseBody).hasFieldOrPropertyWithValue("tokenType", "Bearer");

    ResponseEntity<User> userResponse = getUserRequest(loginResponseBody);
    assertEquals(HttpStatus.OK, userResponse.getStatusCode());
    User userResponseBody = userResponse.getBody();
    assertNotNull(userResponseBody);
    assertEquals(user.getEmail(), userResponseBody.getEmail());
    Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
    assertTrue(userOptional.isPresent());
    assertEquals(userOptional.get().getId(), userResponseBody.getId());
  }

  @Test
  public void logoutShouldSucceed() {
    User user = createUser();

    ResponseEntity<ApiResponse> registerResponse = postRegisterRequestWith(user);
    assertEquals(HttpStatus.CREATED, registerResponse.getStatusCode());

    ResponseEntity<ApiResponse> logoutResponse = postLogout();
    assertEquals(HttpStatus.OK, logoutResponse.getStatusCode());

    ResponseEntity<JwtAuthenticationResponse> loginResponse =
        postLoginRequestWith(new LoginUser(user.getEmail(), user.getPassword()));
    assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
    JwtAuthenticationResponse loginResponseBody = loginResponse.getBody();
    assertNotNull(loginResponseBody);

    ResponseEntity<User> userResponse = getUserRequest(loginResponseBody);
    assertEquals(HttpStatus.OK, userResponse.getStatusCode());

    logoutResponse = postLogout();
    assertEquals(HttpStatus.OK, logoutResponse.getStatusCode());
    assertNotNull(loginResponse.getBody());
  }

  @Test
  public void retrieveUserWithoutTokenFails() {
    ResponseEntity<User> userResponse =
        getUserRequest(new JwtAuthenticationResponse("this-is-not-a-token"));
    assertNotNull(userResponse);
    assertEquals(HttpStatus.UNAUTHORIZED, userResponse.getStatusCode());
  }

  @Test
  public void loginShouldNotSucceedWithNotExistingUser() {
    User user = createUser();
    ResponseEntity<JwtAuthenticationResponse> response =
        postLoginRequestWith(new LoginUser(user.getEmail(), user.getPassword()));
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }

  @Test
  public void loginShouldNotSucceedWithInvalidPassword() {
    User user = createUser();

    ResponseEntity<ApiResponse> registerResponse = postRegisterRequestWith(user);
    assertEquals(HttpStatus.CREATED, registerResponse.getStatusCode());
    assertThat(registerResponse.getBody()).hasFieldOrPropertyWithValue("success", true);

    ResponseEntity<JwtAuthenticationResponse> loginResponse =
        postLoginRequestWith(new LoginUser(user.getEmail(), "Not-the-password"));
    assertEquals(HttpStatus.UNAUTHORIZED, loginResponse.getStatusCode());
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
    return post("/api/auth/register", user, ApiResponse.class);
  }

  protected ResponseEntity<JwtAuthenticationResponse> postLoginRequestWith(LoginUser loginUser) {
    return post("/api/auth/login", loginUser, JwtAuthenticationResponse.class);
  }

  protected ResponseEntity<User> getUserRequest(
      JwtAuthenticationResponse jwtAuthenticationResponse) {
    HttpHeaders headers =
        new HttpHeaders() {
          {
            set(
                "Authorization",
                jwtAuthenticationResponse.getTokenType()
                    + " "
                    + jwtAuthenticationResponse.getToken());
          }
        };
    return restTemplate.exchange(
        "http://localhost:" + port + "/api/auth/user",
        HttpMethod.GET,
        new HttpEntity<>(headers),
        User.class);
  }

  protected ResponseEntity<ApiResponse> postLogout() {
    return post("/api/auth/logout", null, ApiResponse.class);
  }
}
