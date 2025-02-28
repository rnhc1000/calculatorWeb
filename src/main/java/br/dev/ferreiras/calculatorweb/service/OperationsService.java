package br.dev.ferreiras.calculatorweb.service;


import br.dev.ferreiras.calculatorweb.dto.OperationsResponseDto;
import br.dev.ferreiras.calculatorweb.dto.ResponseRandomDto;
import br.dev.ferreiras.calculatorweb.repository.OperationsRepository;
import br.dev.ferreiras.calculatorweb.service.exceptions.InvalidMathRequestException;
import br.dev.ferreiras.calculatorweb.service.exceptions.OutOfBalanceException;
import br.dev.ferreiras.calculatorweb.service.exceptions.RandomProcessingException;
import br.dev.ferreiras.calculatorweb.service.exceptions.UsernameNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
  private final BalanceService balanceService;

  private static final Logger logger = LoggerFactory.getLogger(OperationsService.class);
  private static final String throwExceptionOutOfBalance = "Insufficient funds to do maths! Reload your wallet!";
  final BigDecimal negativeBalance = new BigDecimal("8000863390488707.59991366095112916");

  public OperationsService(final UserService userService, final RandomService randomService,
                           final RecordsService recordsService, final OperationsRepository operationsRepository,
                           final BalanceService balanceService) {
    this.userService = userService;
    this.randomService = randomService;
    this.recordsService = recordsService;
    this.operationsRepository = operationsRepository;
    this.balanceService = balanceService;
  }


  /**
   * @param username requires username - email
   * @param operator define the operator
   * @return random string show on screen
   */
  public ResponseRandomDto executeOperations(final String username, final String operator) {

    if (null == operator) new OperationsResponseDto(this.negativeBalance, BigDecimal.ZERO);

    String result = "0";

    final var user = this.userService.getUsername(username).orElseThrow(
        () -> new UsernameNotFoundException("Username invalid!"));
    logger.info("User Authenticated: {}", user.getUsername());

    BigDecimal balance = this.userService.getBalance(username);
    OperationsService.logger.info("Balance: {}", balance);

    final BigDecimal cost = this.userService.getOperationCostByOperation(operator);
    OperationsService.logger.info("Cost: {}", cost);

    if (this.balanceService.balanceCheck(operator, balance)) {

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
    } else {
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
      final BigDecimal operandOne, final BigDecimal operandTwo,
      final String operator, final String username) {

    BigDecimal result = new BigDecimal("0");
    final boolean deleted = false;
    final var user = this.userService.getUsername(username);
    OperationsService.logger.info("User: {}", user.orElseThrow().getUsername());

    var balance = this.userService.getBalance(username);
    OperationsService.logger.info("balance: {}", balance);

    final var cost = this.userService.getOperationCostByOperation(operator);
    OperationsService.logger.info("cost: {}", cost);


    switch (operator) {

      case "addition", "" -> {

        if (this.balanceService.balanceCheck(operator, balance)) {
          balance = balance.subtract(cost);
          this.userService.updateBalance(username, balance);
          final AdditionService additionService = new AdditionService();
          result = additionService.mathOperations(operandOne, operandTwo);
          this.recordsService.saveRecordsRandom(username, operandOne, operandTwo,
              operator, result, cost, deleted);

        } else {

          throw new OutOfBalanceException(OperationsService.throwExceptionOutOfBalance);
        }
      }

      case "subtraction" -> {

        if (this.balanceService.balanceCheck(operator, balance)) {
          balance = balance.subtract(cost);
          this.userService.updateBalance(username, balance);
          final SubtractionService subtractionService = new SubtractionService();
          result = subtractionService.mathOperations(operandOne, operandTwo);
          this.recordsService.saveRecordsRandom(username, operandOne, operandTwo,
              operator, result, cost, deleted);

        } else {

          throw new OutOfBalanceException(OperationsService.throwExceptionOutOfBalance);
        }
      }

      case "multiplication" -> {

        if (this.balanceService.balanceCheck(operator, balance)) {
          balance = balance.subtract(cost);
          this.userService.updateBalance(username, balance);
          final MultiplicationService multiplicationService = new MultiplicationService();
          result = multiplicationService.mathOperations(operandOne, operandTwo);
          this.recordsService.saveRecordsRandom(username, operandOne, operandTwo,
              operator, result, cost, deleted);

        } else {

          throw new OutOfBalanceException(OperationsService.throwExceptionOutOfBalance);

        }
      }

      case "division" -> {

        if (operandTwo.compareTo(BigDecimal.ZERO) == 0) {
          logger.info("Comparison ok-> Negative Number, {}", operandTwo);
          throw new InvalidMathRequestException("Illegal math operation!");
        }
        if (this.balanceService.balanceCheck(operator, balance)) {
          balance = balance.subtract(cost);
          this.userService.updateBalance(username, balance);
          final DivisionService divisionService = new DivisionService();
          result = divisionService.mathOperations(operandOne, operandTwo);
          this.recordsService.saveRecordsRandom(username, operandOne, operandTwo,
              operator, result, cost, deleted);

        } else {

          logger.info("Result -> Negative Number, {}", operandTwo);

          throw new OutOfBalanceException(OperationsService.throwExceptionOutOfBalance);

        }
      }

      case "square_root" -> {
        if (operandOne.signum() < 0) {

          throw new InvalidMathRequestException("Illegal math operation!");
        }

        if (this.balanceService.balanceCheck(operator, balance)) {
          balance = balance.subtract(cost);
          this.userService.updateBalance(username, balance);
          final SquareRootService squareRootService = new SquareRootService();
          result = squareRootService.mathOperations(operandOne);
          this.recordsService.saveRecordsRandom(username, operandOne, operandTwo,
              operator, result, cost, deleted);

        } else {

          throw new OutOfBalanceException(OperationsService.throwExceptionOutOfBalance);

        }
      }

      case null, default -> {

        return new OperationsResponseDto(result, balance);

      }

    }

    return new OperationsResponseDto(result, balance);
  }

  /**
   * @return list of operators implemented
   */
  public List<Object> getOperationsCost() {

    return this.operationsRepository.findAllCosts();
  }


}

