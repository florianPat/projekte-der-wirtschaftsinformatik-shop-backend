package fhdw.pdw;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration.FlywayConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

@Configuration
public class FlywaySpringIntegration extends FlywayConfiguration {
  @Primary
  @Bean(name = "flywayInitializer")
  @DependsOn("springUtility")
  public FlywayMigrationInitializer flywayInitializer(Flyway flyway) {
    return super.flywayInitializer(flyway, null);
  }
}
