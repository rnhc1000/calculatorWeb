package br.dev.ferreiras.calculatorweb.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
/** Manages database migration systemically
 * @author ricardo@ferreiras.dev.br
 * @version 1.1.030901
 * @since 08/2024
 */
//@Configuration
public class FlyWayConfiguration {

  @Bean
  FlywayMigrationInitializer flywayInitializer(Flyway flyway) {
//    return new FlywayMigrationInitializer(flyway, (f) ->{} );
    return new FlywayMigrationInitializer(flyway, Flyway::migrate);

  }

  /**
   *
   * @param flyway flyway object
   * @return database update if a change is made
   */
  @Bean
  @DependsOn ("entityManagerFactory")
  FlywayMigrationInitializer delayedFlywayInitializer(Flyway flyway) {
    return new FlywayMigrationInitializer(flyway, Flyway::migrate);
    /*
       return new FlywayMigrationInitializer(flyway, new FlywayMigrationStrategy() {
      @Override
      public void migrate(Flyway flyway) {

        flyway.migrate();
      }
    })
     */
  }
}
