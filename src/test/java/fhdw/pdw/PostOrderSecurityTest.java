package fhdw.pdw;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fhdw.pdw.model.dto.ApiResponse;
import fhdw.pdw.model.dto.OrderItemDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostOrderSecurityTest extends AbstractHttpRequestTest {
  @Test
  public void testPostOrderNotPermittedOnUnauthorized() {
    ResponseEntity<ApiResponse> response =
        post("/api/order", new OrderItemDto[2], ApiResponse.class);

    System.out.println(response);
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }
}
