package br.dev.ferreiras.calculatorWeb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Slf4j
@Service
public class OperationsService {


  private RandomService randomService;

  public BigDecimal executeOperations(BigDecimal operandOne, BigDecimal operandTwo, String operator) {

    BigDecimal result = new BigDecimal("0");
    if (operator == null) return new BigDecimal("0.0");
    try {
      switch (operator) {

        case "addition" -> result = addOperands(operandOne, operandTwo);
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

