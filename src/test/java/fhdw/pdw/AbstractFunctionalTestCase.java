package fhdw.pdw;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AbstractFunctionalTestCase {

  @Autowired Flyway flyway;

  @RegisterExtension
  protected static final GreenMailExtension greenMail =
      new GreenMailExtension(ServerSetupTest.SMTP)
          .withConfiguration(GreenMailConfiguration.aConfig().withUser("duke", "springboot"))
          .withPerMethodLifecycle(false);

  @BeforeEach
  void setUp() {
    this.flyway.migrate();
  }

  @AfterEach
  void tearDown() {
    this.flyway.clean();
  }
}
