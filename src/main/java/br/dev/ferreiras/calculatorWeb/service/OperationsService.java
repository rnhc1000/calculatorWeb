package br.dev.ferreiras.calculatorWeb.service;


import br.dev.ferreiras.calculatorWeb.entity.Operation;
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

/** This service class deal on how to make each operation, math or random string
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

  public OperationsService(UserService userService, RandomService randomService, RecordsService recordsService, OperationsRepository operationsRepository) {
    this.userService = userService;
    this.randomService = randomService;
    this.recordsService = recordsService;
    this.operationsRepository = operationsRepository;
  }

  /**
   *
   * @param username requires username - email
   * @param operator define the operator
   * @return random string show on screen
   */
  public String executeOperations(String username, String operator) {

    if (operator == null) return "";

    String result = "0";

    var user = userService.getUsername(username);
    logger.info("Usuario: {}", user.get().getUsername());

    BigDecimal balance = userService.getBalance(username);
    logger.info("Balance: {}", balance);

    BigDecimal cost = userService.getOperationCostByOperation(operator);
    logger.info("Cost: {}", cost);

    if (balance.compareTo(cost) > 0) {

      balance = balance.subtract(cost);
      userService.updateBalance(username, balance);
      String randomApiResponse = randomService.makeApiRequest();
      logger.info("ApiRandomResponse: {}", randomApiResponse);
      ObjectMapper objectMapper = new ObjectMapper();

      try {
        JsonNode rootNode = objectMapper.readTree(randomApiResponse);
        JsonNode stringNode = rootNode.path("result").path("random").path("data");

        logger.info("Inside Jackson decoder...");
        logger.info("JsonNode: {}", stringNode.path(0).toPrettyString());

        result = stringNode.path(0).toPrettyString();
        recordsService.saveRecordsRandom(username, operator, result, cost );

      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    }

    return result;
  }

  /**
   *
   * @param operandOne first operand
   * @param operandTwo second operand
   * @param operator   operator
   * @param username   username
   * @return string result
   */
  public BigDecimal executeOperations(
          BigDecimal operandOne, BigDecimal operandTwo, String operator, String username) {

    if (operator == null) return new BigDecimal("0.0");

    BigDecimal result = new BigDecimal("0");

    var user = userService.getUsername(username);
    logger.info("usuario: {}", user.get().getUsername());

    var balance = userService.getBalance(username);
    logger.info("balance: {}", balance);

    var cost = userService.getOperationCostByOperation(operator);
    logger.info("cost: {}", cost);

    try {

      switch (operator) {

        case "addition" -> {

          if (balance.compareTo(cost) > 0) {

            balance = balance.subtract(cost);
            userService.updateBalance(username, balance);
            result = addOperands(operandOne, operandTwo);
            recordsService.saveRecordsRandom( username, operandOne, operandTwo, operator, result, cost );

          } else {

            result = BigDecimal.valueOf(-1L);

          }
        }

        case "subtraction" -> {

          if (balance.compareTo(cost) > 0) {

            balance = balance.subtract(cost);
            userService.updateBalance(username, balance);
            result = subtractOperands(operandOne, operandTwo);
            recordsService.saveRecordsRandom( username, operandOne, operandTwo, operator, result, cost );

          } else {

            result = BigDecimal.valueOf(-1L);

          }
        }

        case "multiplication" -> {

          if (balance.compareTo(cost) > 0) {

            balance = balance.subtract(cost);
            userService.updateBalance(username, balance);
            result = multiplyOperands(operandOne, operandTwo);
            recordsService.saveRecordsRandom( username, operandOne, operandTwo, operator, result, cost );

          } else {

            result = BigDecimal.valueOf(-1L);

          }
        }

        case "division" -> {

          if (balance.compareTo(cost) > 0) {

            balance = balance.subtract(cost);
            userService.updateBalance(username, balance);
            result = divideOperands(operandOne, operandTwo);
            recordsService.saveRecordsRandom( username, operandOne, operandTwo, operator, result, cost );

          } else {

            result = BigDecimal.valueOf(-1L);

          }
        }

        case "square_root" -> {
          if (balance.compareTo(cost) > 0) {

            balance = balance.subtract(cost);
            userService.updateBalance(username, balance);
            result = squareRoot(operandOne);
            recordsService.saveRecordsRandom( username, operandOne, operandTwo, operator, result, cost );

          } else {

            result = BigDecimal.valueOf(-1L);

          }
        }

      }

      return result;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   *
   * @param operandOne first operand
   * @param operandTwo second operand
   * @return sum of two operands
   */
  public BigDecimal addOperands(BigDecimal operandOne, BigDecimal operandTwo) {
    return operandOne.add(operandTwo);
  }

  /**
   *
   * @param operandOne first operand
   * @param operandTwo second operand
   * @return subtraction of two operands
   */
  public BigDecimal subtractOperands(BigDecimal operandOne, BigDecimal operandTwo) {
    return operandOne.subtract(operandTwo);
  }

  /**
   *
   * @param operandOne first operand
   * @param operandTwo second operand
   * @return product of two operands
   */
  public BigDecimal multiplyOperands(BigDecimal operandOne, BigDecimal operandTwo) {
    return operandOne.multiply(operandTwo);
  }

  /**
   *
   * @param operandOne first operand
   * @param operandTwo second operand
   * @return division of two operands
   * @throws Exception second operation can not be equal to zero
   */
  public BigDecimal divideOperands(BigDecimal operandOne, BigDecimal operandTwo) throws Exception {
    BigDecimal division = BigDecimal.ZERO;
    try {

      division = operandOne.divide(operandTwo, 4, RoundingMode.CEILING);

    } catch (ArithmeticException ex) {

      logger.info("Division by zero not allowed");
      throw new ForbiddenException("Division by zero not allowed!");

    }
    return division;
  }

  /**
   *
   * @param operandOne first operand
   * @return square root of first operand
   * @throws Exception no negative numbers supported
   */
  public BigDecimal squareRoot(BigDecimal operandOne) throws Exception{
    MathContext mathContext = new MathContext(4);
    BigDecimal square = BigDecimal.ZERO;
    try {

        square =  operandOne.sqrt(mathContext);

    } catch (ArithmeticException ex) {

      logger.info("Square root of negative numbers not allowed");
      throw new ForbiddenException("Only positive numbers");

    }
    return square;
  }

  /**
   *
   * @return list of operators implemented
   */
  public List<Operation> getOperators() {
    return operationsRepository.findAll();
  }
}

