package br.dev.ferreiras.calculatorweb.service;

import br.dev.ferreiras.calculatorweb.dto.OperationsResponseDto;
import br.dev.ferreiras.calculatorweb.dto.ResponseRandomDto;
import br.dev.ferreiras.calculatorweb.repository.OperationsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
class OperationsServiceTest {

  @InjectMocks
  private OperationsService operationsService;

  @Mock
  private OperationsRepository operationsRepository;

  private RandomService randomService;

  private UserService userService;

  private RecordsService recordsService;

  private ResponseRandomDto responseRandomDto;

  private OperationsResponseDto operationsResponseDto;



  @BeforeEach
  public void setUp() {
    openMocks(this);
  }

  @Test
  void executeOperations() {
  }

  @Test
  void testExecuteOperations() {
  }

  @Test
  void addOperands() {
  }

  @Test
  void subtractOperands() {
  }

  @Test
  void multiplyOperands() {
  }

  @Test
  void divideOperands() {
  }

  @Test
  void squareRoot() {
  }

  @Test
  void getOperationsCost() {
  }
}