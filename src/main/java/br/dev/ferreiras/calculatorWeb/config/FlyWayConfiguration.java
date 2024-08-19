package br.dev.ferreiras.calculatorWeb.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

//@Configuration
public class FlyWayConfiguration {

  @Bean
  FlywayMigrationInitializer flywayInitializer(Flyway flyway) {
    return new FlywayMigrationInitializer(flyway, (f) ->{} );
  }

  @Bean
  @DependsOn ("entityManagerFactory")
  FlywayMigrationInitializer delayedFlywayInitializer(Flyway flyway) {
    return new FlywayMigrationInitializer(flyway, new FlywayMigrationStrategy() {
      @Override
      public void migrate(Flyway flyway) {

        flyway.migrate();
      }
    });
  }
}
