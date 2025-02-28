package br.dev.ferreiras.calculatorweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
public class CalculatorWebApplication implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(CalculatorWebApplication.class);

  /**
   * Entry point java App execution
   *
   * @param args no args
   */
  public static void main(final String[] args) {

    SpringApplication.run(CalculatorWebApplication.class, args);
  }

  /**
   * Indicates starting time, zone and Java version
   *
   * @param args there are no args
   * @throws Exception any method can trigger an Exception
   */
  @Override
  public void run(final String... args) throws Exception {
    final ZonedDateTime zonedDateTime = ZonedDateTime.now(ZonedDateTime.now().getZone());
    final String javaVersion = System.getProperty("java.version");
    final int numOfCores = Runtime.getRuntime().availableProcessors();
    final long totalMemory = Runtime.getRuntime().totalMemory() / 1000_000L;

    if (CalculatorWebApplication.logger.isInfoEnabled()) {
      CalculatorWebApplication.logger.info("Calculator Web started running at zone {}, running java version {}, on top " +
                                           "of {} cores and consuming {}MB of RAM!",
          zonedDateTime,
          javaVersion,
          numOfCores,
          totalMemory
      );
    }
  }

  public List<String> checkSystem() {
    final String javaVersion = System.getProperty("java_version");
    final int numberOfCores = Runtime.getRuntime().availableProcessors();
    final List<String> requirements = new ArrayList<>();
    requirements.add(javaVersion);
    requirements.add(Integer.toString(numberOfCores));

    return requirements;
  }
}
