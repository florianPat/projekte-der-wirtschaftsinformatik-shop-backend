package fhdw.pdw;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractFunctionalTestCase {

  @Autowired Flyway flyway;

  @BeforeEach
  void setUp() {
    this.flyway.migrate();
  }

  @AfterEach
  void tearDown() {
    this.flyway.clean();
  }
}
