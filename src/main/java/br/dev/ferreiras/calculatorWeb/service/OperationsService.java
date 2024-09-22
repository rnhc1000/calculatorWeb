package br.dev.ferreiras.calculatorWeb.service;


import br.dev.ferreiras.calculatorWeb.dto.OperationsResponseDto;
import br.dev.ferreiras.calculatorWeb.dto.ResponseRandomDto;
import br.dev.ferreiras.calculatorWeb.entity.Operation;
import br.dev.ferreiras.calculatorWeb.entity.Operations;
import br.dev.ferreiras.calculatorWeb.repository.OperationsRepository;
import br.dev.ferreiras.calculatorWeb.service.exceptions.ForbiddenException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

/**
 * This service class deal on how to make each operation, math or random string
 *
 * @author ricardo@ferreiras.dev.br
 * @version 1.1.030901
 * @since 08/2024
 */

@Service
public class OperationsService {

  private final RandomService randomService;
  private final UserService userService;
  private final RecordsService recordsService;
  private final OperationsRepository operationsRepository;
  private static final Logger logger = LoggerFactory.getLogger(OperationsService.class);
  final BigDecimal BALANCE_NEGATIVE = new BigDecimal("8000863390488707.59991366095112916");

  public OperationsService(final UserService userService, final RandomService randomService, final RecordsService recordsService, final OperationsRepository operationsRepository) {
    this.userService = userService;
    this.randomService = randomService;
    this.recordsService = recordsService;
    this.operationsRepository = operationsRepository;
  }

  /**
   * @param username requires username - email
   * @param operator define the operator
   * @return random string show on screen
   */
  public ResponseRandomDto executeOperations(final String username, final String operator) {

    if (null == operator) new OperationsResponseDto(this.BALANCE_NEGATIVE, BigDecimal.ZERO);

    String result = "0";

    final var user = this.userService.getUsername(username);
    OperationsService.logger.info("Usuario: {}", user.get().getUsername());

    BigDecimal balance = this.userService.getBalance(username);
    OperationsService.logger.info("Balance: {}", balance);

    final BigDecimal cost = this.userService.getOperationCostByOperation(operator);
    OperationsService.logger.info("Cost: {}", cost);

    if (0 < balance.compareTo(cost)) {

      balance = balance.subtract(cost);
      this.userService.updateBalance(username, balance);

      final String randomApiResponse = this.randomService.makeApiRequest();
      OperationsService.logger.info("ApiRandomResponse: {}", randomApiResponse);

      final ObjectMapper objectMapper = new ObjectMapper();

      try {
        final JsonNode rootNode = objectMapper.readTree(randomApiResponse);
        final JsonNode stringNode = rootNode.path("result").path("random").path("data");

        OperationsService.logger.info("Inside Jackson decoder...");
        OperationsService.logger.info("JsonNode: {}", stringNode.path(0).toPrettyString());

        result = stringNode.path(0).toPrettyString();
        this.recordsService.saveRecordsRandom(username, operator, result, cost);

      } catch (final JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    }
    return new ResponseRandomDto(result, balance);
  }

  /**
   * @param operandOne first operand
   * @param operandTwo second operand
   * @param operator   operator
   * @param username   username
   * @return string result
   */
  public OperationsResponseDto executeOperations(
          BigDecimal operandOne, BigDecimal operandTwo, final String operator, final String username) {

    if (null == operator) new OperationsResponseDto(this.BALANCE_NEGATIVE, BigDecimal.ZERO);

    BigDecimal result = new BigDecimal("0");

    final var user = this.userService.getUsername(username);
    OperationsService.logger.info("usuario: {}", user.get().getUsername());

    var balance = this.userService.getBalance(username);
    OperationsService.logger.info("balance: {}", balance);

    final var cost = this.userService.getOperationCostByOperation(operator);
    OperationsService.logger.info("cost: {}", cost);

    try {

      switch (operator) {

        case "addition" -> {

          if (0 < balance.compareTo(cost)) {

            operandOne = (null == operandOne ? BigDecimal.ZERO : operandOne);
            operandTwo = (null == operandTwo ? BigDecimal.ZERO : operandTwo);
            balance = balance.subtract(cost);
            this.userService.updateBalance(username, balance);
            result = this.addOperands(operandOne, operandTwo);
            this.recordsService.saveRecordsRandom(username, operandOne, operandTwo, operator, result, cost);

          } else {

            result = this.BALANCE_NEGATIVE;
          }
        }

        case "subtraction" -> {

          if (0 < balance.compareTo(cost)) {
            operandOne = (null == operandOne ? BigDecimal.ZERO : operandOne);
            operandTwo = (null == operandTwo ? BigDecimal.ZERO : operandTwo);
            balance = balance.subtract(cost);
            this.userService.updateBalance(username, balance);
            result = this.subtractOperands(operandOne, operandTwo);
            this.recordsService.saveRecordsRandom(username, operandOne, operandTwo, operator, result, cost);

          } else {

            result = this.BALANCE_NEGATIVE;
          }
        }

        case "multiplication" -> {

          if (balance.compareTo(cost) > 0) {
            operandOne = (null == operandOne ? BigDecimal.ZERO : operandOne);
            operandTwo = (null == operandTwo ? BigDecimal.ZERO : operandTwo);
            balance = balance.subtract(cost);
            this.userService.updateBalance(username, balance);
            result = this.multiplyOperands(operandOne, operandTwo);
            this.recordsService.saveRecordsRandom(username, operandOne, operandTwo, operator, result, cost);

          } else {

            result = this.BALANCE_NEGATIVE;
          }
        }

        case "division" -> {
          operandOne = (null == operandOne ? BigDecimal.ZERO : operandOne);
          operandTwo = (null == operandTwo ? BigDecimal.ZERO : operandTwo);
          if (0 < balance.compareTo(cost)) {

            balance = balance.subtract(cost);
            this.userService.updateBalance(username, balance);
            result = this.divideOperands(operandOne, operandTwo);
            this.recordsService.saveRecordsRandom(username, operandOne, operandTwo, operator, result, cost);

          } else {

            result = this.BALANCE_NEGATIVE;
          }
        }

        case "square_root" -> {
          if (0 < balance.compareTo(cost)) {
            operandOne = (null == operandOne ? BigDecimal.ZERO : operandOne);
            balance = balance.subtract(cost);
            this.userService.updateBalance(username, balance);
            result = this.squareRoot(operandOne);
            this.recordsService.saveRecordsRandom(username, operandOne, operandTwo, operator, result, cost);

          } else {

            result = this.BALANCE_NEGATIVE;
          }
        }

        case null -> {
        }

        default -> {

          result = this.BALANCE_NEGATIVE;
        }

      }

      return new OperationsResponseDto(result, balance);
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * @param operandOne first operand
   * @param operandTwo second operand
   * @return sum of two operands
   */
  public BigDecimal addOperands(BigDecimal operandOne, BigDecimal operandTwo) {
    return operandOne.add(operandTwo);
  }

  /**
   * @param operandOne first operand
   * @param operandTwo second operand
   * @return subtraction of two operands
   */
  public BigDecimal subtractOperands(BigDecimal operandOne, BigDecimal operandTwo) {
    return operandOne.subtract(operandTwo);
  }

  /**
   * @param operandOne first operand
   * @param operandTwo second operand
   * @return product of two operands
   */
  public BigDecimal multiplyOperands(final BigDecimal operandOne, final BigDecimal operandTwo) {
    return operandOne.multiply(operandTwo);
  }

  /**
   * @param operandOne first operand
   * @param operandTwo second operand
   * @return division of two operands
   * @throws Exception second operation can not be equal to zero
   */
  public BigDecimal divideOperands(final BigDecimal operandOne, final BigDecimal operandTwo) throws Exception {
    BigDecimal division = BigDecimal.ZERO;
    try {

      division = operandOne.divide(operandTwo, 4, RoundingMode.CEILING);

    } catch (final ArithmeticException ex) {

      OperationsService.logger.info("Division by zero not allowed");
      throw new ForbiddenException("Division by zero not allowed!");

    }
    return division;
  }

  /**
   * @param operandOne first operand
   * @return square root of first operand
   * @throws Exception no negative numbers supported
   */
  public BigDecimal squareRoot(final BigDecimal operandOne) throws Exception {
    MathContext mathContext = new MathContext(4);
    BigDecimal square = BigDecimal.ZERO;
    try {

      square = operandOne.sqrt(mathContext);

    } catch (final ArithmeticException ex) {

      OperationsService.logger.info("Square root of negative numbers not allowed");
      throw new ForbiddenException("Only positive numbers");

    }
    return square;
  }

  /**
   * @return list of operators implemented
   */
  public List<Object> getOperationsCost() {
    return this.operationsRepository.findAllCosts();
  }
}

