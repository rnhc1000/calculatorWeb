package br.dev.ferreiras.calculatorweb;

import br.dev.ferreiras.calculatorweb.service.OperationsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
class CalculatorWebApplicationTests {

	@Mock
	private OperationsService operationsService;

	@Test
	void main() {
		CalculatorWebApplication.main(new String[] {});
	}

	@Test
	void run() {
		when(this.operationsService.checkSystem()).thenReturn(Collections.singletonList("21"));
		assertEquals( "21.0.4",System.getProperty("java.version"));
		assertEquals(4, Runtime.getRuntime().availableProcessors());
	}
}
