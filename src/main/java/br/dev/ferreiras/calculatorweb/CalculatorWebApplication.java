package br.dev.ferreiras.calculatorweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@SpringBootApplication
public class CalculatorWebApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(CalculatorWebApplication.class);

	/** Entry point java App execution
	 *
	 * @param args no args
	 */
	public static void main(final String[] args) {
		SpringApplication.run(CalculatorWebApplication.class, args);
	}

	/** Indicates starting time, zone and Java version
	 *
	 * @param args there are no args
	 * @throws Exception any method can trigger an Exception
	 */
	@Override
	public void run(final String... args) throws Exception {
		final LocalDateTime localTime = LocalDateTime.now();
		final ZonedDateTime zonedDateTime = ZonedDateTime.now(ZonedDateTime.now().getZone());
		final String javaVersion = System.getProperty("java.version");
		final int  numOfCores = Runtime.getRuntime().availableProcessors();

    if (CalculatorWebApplication.logger.isInfoEnabled()) {
      CalculatorWebApplication.logger.info("Calculator Web started running at {}, zone {}, running java version {}, on top of {} cores",
              localTime,
              zonedDateTime,
              javaVersion,
              numOfCores
      );
    }
  }
}
