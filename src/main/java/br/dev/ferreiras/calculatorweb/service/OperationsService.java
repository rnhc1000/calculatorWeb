package br.dev.ferreiras.calculatorweb.service;


import br.dev.ferreiras.calculatorweb.dto.OperationsResponseDto;
import br.dev.ferreiras.calculatorweb.dto.ResponseRandomDto;
import br.dev.ferreiras.calculatorweb.repository.OperationsRepository;
import br.dev.ferreiras.calculatorweb.service.exceptions.InvalidMathRequestException;
import br.dev.ferreiras.calculatorweb.service.exceptions.OutOfBalanceException;
import br.dev.ferreiras.calculatorweb.service.exceptions.RandomProcessingException;
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
  private static final String throwExceptionOutOfBalance = "Insufficient funds to do maths! Reload your wallet!";
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
    OperationsService.logger.info("Usuario: {}", user.orElseThrow().getUsername());

    BigDecimal balance = this.userService.getBalance(username);
    OperationsService.logger.info("Balance: {}", balance);

    final BigDecimal cost = this.userService.getOperationCostByOperation(operator);
    OperationsService.logger.info("Cost: {}", cost);

    if (0 <= balance.compareTo(cost)) {

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
        throw new RandomProcessingException("JSON Processing error");
      }
      return new ResponseRandomDto(result, balance);
    }  else {
      throw new OutOfBalanceException(OperationsService.throwExceptionOutOfBalance);
    }

  }

  /**
   * @param operandOne first operand
   * @param operandTwo second operand
   * @param operator   operator
   * @param username   username
   * @return string result
   */
  public OperationsResponseDto executeOperations(
          BigDecimal operandOne, BigDecimal operandTwo,
          final String operator, final String username) {

    if (null == operator) new OperationsResponseDto(this.BALANCE_NEGATIVE, BigDecimal.ZERO);

    BigDecimal result = new BigDecimal("0");

    final var user = this.userService.getUsername(username);
    OperationsService.logger.info("usuario: {}", user.orElseThrow().getUsername());

    var balance = this.userService.getBalance(username);
    OperationsService.logger.info("balance: {}", balance);

    final var cost = this.userService.getOperationCostByOperation(operator);
    OperationsService.logger.info("cost: {}", cost);

    switch (operator) {

      case "addition" -> {

        if (0 <= balance.compareTo(cost)) {

          operandOne = (null == operandOne ? BigDecimal.ZERO : operandOne);
          operandTwo = (null == operandTwo ? BigDecimal.ZERO : operandTwo);
          balance = balance.subtract(cost);
          this.userService.updateBalance(username, balance);
          result = this.addOperands(operandOne, operandTwo);
          this.recordsService.saveRecordsRandom(username, operandOne, operandTwo, operator, result, cost);

        } else {

          result = this.BALANCE_NEGATIVE;
          throw new OutOfBalanceException(OperationsService.throwExceptionOutOfBalance);
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
          throw new OutOfBalanceException(OperationsService.throwExceptionOutOfBalance);
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
          throw new OutOfBalanceException(OperationsService.throwExceptionOutOfBalance);

        }
      }

      case "division" -> {
        operandOne = (null == operandOne ? BigDecimal.ZERO : operandOne);
        operandTwo = (null == operandTwo ? BigDecimal.ZERO : operandTwo);
        if (0 == operandTwo.compareTo(BigDecimal.ZERO)) {
          logger.info("Comparison ok-> Negative Number, {}", operandTwo);
          throw new InvalidMathRequestException("Illegal math operation!");
        }
        if (0 <= balance.compareTo(cost)) {
          balance = balance.subtract(cost);
          this.userService.updateBalance(username, balance);
          result = this.divideOperands(operandOne, operandTwo);
          this.recordsService.saveRecordsRandom(username, operandOne, operandTwo, operator, result, cost);

        } else {

          result = this.BALANCE_NEGATIVE;
          logger.info("Result -> Negative Number, {}", operandTwo);

          throw new OutOfBalanceException(OperationsService.throwExceptionOutOfBalance);

        }
      }

      case "square_root" -> {
        operandOne = (null == operandOne ? BigDecimal.ZERO : operandOne);
        if (0 > operandOne.compareTo(BigDecimal.ZERO)) {

          throw new InvalidMathRequestException("Illegal math operation!");
        }

        if (0 <= balance.compareTo(cost)) {
          balance = balance.subtract(cost);
          this.userService.updateBalance(username, balance);
          result = this.squareRoot(operandOne);
          this.recordsService.saveRecordsRandom(username, operandOne, operandTwo, operator, result, cost);

        } else {

          result = this.BALANCE_NEGATIVE;
          throw new OutOfBalanceException(OperationsService.throwExceptionOutOfBalance);

        }
      }

      case null -> {
      }

      default -> {

        result = this.BALANCE_NEGATIVE;
      }

    }

    return new OperationsResponseDto(result, balance);
  }

  /**
   * @param operandOne first operand
   * @param operandTwo second operand
   * @return sum of two operands
   */
  public BigDecimal addOperands(final BigDecimal operandOne, final BigDecimal operandTwo) {
    return operandOne.add(operandTwo);
  }

  /**
   * @param operandOne first operand
   * @param operandTwo second operand
   * @return subtraction of two operands
   */
  public BigDecimal subtractOperands(final BigDecimal operandOne, final BigDecimal operandTwo) {
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
   * @throws InvalidMathRequestException no / by zero
   */
  public BigDecimal divideOperands(final BigDecimal operandOne, final BigDecimal operandTwo) {
    BigDecimal division = BigDecimal.ZERO;

    try {

      division = operandOne.divide(operandTwo, 4, RoundingMode.CEILING);

    } catch (final ArithmeticException ex) {

      OperationsService.logger.info("/ by zero not allowed");
      //OperationsService.logger.info(String.valueOf(ex));

      throw new InvalidMathRequestException("/ by zero");

    }
    return division;
  }

  /**
   * @param operandOne first operand
   * @return square root of first operand
   * @throws InvalidMathRequestException no negative numbers supported
   */
  public BigDecimal squareRoot(final BigDecimal operandOne) {
    final MathContext mathContext = new MathContext(4);
    BigDecimal square = BigDecimal.ZERO;
    try {

      square = operandOne.sqrt(mathContext);

    } catch (final ArithmeticException ex) {

      OperationsService.logger.info("Square root of negative numbers not allowed");
      throw new InvalidMathRequestException("Only positive numbers");

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

