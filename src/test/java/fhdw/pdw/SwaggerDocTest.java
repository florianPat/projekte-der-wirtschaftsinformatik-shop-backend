package fhdw.pdw;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SwaggerDocTest extends AbstractHttpRequestTest {
  @Test
  public void apiDocuShouldBeAvailable() {
    ResponseEntity<Object> response =
        restTemplate.getForEntity("http://localhost:" + port + "/v2/api-docs", Object.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
}
