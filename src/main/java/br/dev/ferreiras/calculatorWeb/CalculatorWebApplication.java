package br.dev.ferreiras.calculatorWeb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

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
    CalculatorWebApplication.logger.info("Calculator Web started running at {}, zone {}, running java version {}",
						LocalDateTime.now(),
						ZonedDateTime.now().getZone(),
						System.getProperty("java.version")
		);
	}
}
