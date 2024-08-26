package br.dev.ferreiras.calculatorWeb.service;


import br.dev.ferreiras.calculatorWeb.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;


@Service
public class OperationsService {

  private final RandomService randomService;
  private final UserService userService;
  private static final Logger logger = LoggerFactory.getLogger(OperationsService.class);

  public OperationsService(UserService userService, RandomService randomService) {
    this.userService = userService;
    this.randomService = randomService;
  }

  public BigDecimal executeOperations(
          BigDecimal operandOne, BigDecimal operandTwo, String operator, String username) {

    BigDecimal result = new BigDecimal("0");
    if (operator == null) return new BigDecimal("0.0");
    try {
      switch (operator) {

        case "addition" -> {
          var user = userService.getUsername(username);
          logger.info("usuario: {}", user);
          var balance = userService.getBalance(username);
          logger.info("balance: {}", balance);

          var cost = userService.getOperationCostById(1L);
          logger.info("cost: {}", cost);

          if ( balance.compareTo(cost) == 0 ) {
            balance = balance.subtract(cost);
            userService.updateBalance(username, balance);
            result = addOperands(operandOne, operandTwo);
          } else {
            result = BigDecimal.valueOf(-1L);
          }

        }
        case "subtraction" -> result = subtractOperands(operandOne,operandTwo);
        case "multiplication" -> result = multiplyOperands(operandOne,operandTwo);
        case "division" -> result = divideOperands(operandOne, operandTwo);
        case "squareRoot" -> result = squareRoot(operandOne);
      }
      return result;
    } catch (RuntimeException e) {
      throw new RuntimeException(e);
    }
  }

  public BigDecimal addOperands(BigDecimal operandOne, BigDecimal operandTwo) {
    return operandOne.add(operandTwo);
  }

  public BigDecimal subtractOperands(BigDecimal operandOne, BigDecimal operandTwo) {
    return operandOne.subtract(operandTwo);
  }
  public BigDecimal multiplyOperands(BigDecimal operandOne, BigDecimal operandTwo) {
    return operandOne.multiply(operandTwo);
  }
  private BigDecimal divideOperands(BigDecimal operandOne, BigDecimal operandTwo) {
    BigDecimal division = BigDecimal.ZERO;
    try {
      division = operandOne.divide(operandTwo, 4, RoundingMode.CEILING);
    } catch (ArithmeticException ex) {
      System.out.println("Can not divide by zero");
    }
    return division;
  }

  private BigDecimal squareRoot(BigDecimal operandOne) {
    MathContext mathContext = new MathContext(4);
    return operandOne.sqrt(mathContext);
  }
}

