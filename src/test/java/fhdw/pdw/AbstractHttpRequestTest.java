package fhdw.pdw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractHttpRequestTest extends AbstractFunctionalTestCase {
  @LocalServerPort protected int port;

  @Autowired protected TestRestTemplate restTemplate;

  protected <Response, Request> ResponseEntity<Response> post(
      String path, Request request, Class<Response> responseClass) {
    return restTemplate.postForEntity("http://localhost:" + port + path, request, responseClass);
  }
}
