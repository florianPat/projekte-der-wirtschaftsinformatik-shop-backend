package fhdw.pdw;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration.FlywayConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

@Configuration
@EnableConfigurationProperties(FlywayProperties.class)
@ConditionalOnProperty(prefix = "spring.flyway", name = "enabled", matchIfMissing = true)
public class FlywaySpringIntegration extends FlywayConfiguration {
  /**
   * Um den Spring Container in Flyway Java Migrationen zu verwenden, wird vor dem Starten von
   * Flyway das Bean SpringUtility initialisert
   */
  @Primary
  @Bean(name = "flywayInitializer")
  @DependsOn("springUtility")
  public FlywayMigrationInitializer flywayInitializer(
      Flyway flyway, ObjectProvider<FlywayMigrationStrategy> migrationStrategy) {
    return super.flywayInitializer(flyway, migrationStrategy);
  }
}
