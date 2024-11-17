package br.dev.ferreiras.calculatorweb.service;

import br.dev.ferreiras.calculatorweb.contracts.AnyNumberOfOperations;

import java.math.BigDecimal;

public class Addition implements AnyNumberOfOperations {

  /**
   * @param operands ...first operand
   * @param operands ...second operand
   * @return sum of two operands
   */
  @Override
  public BigDecimal mathOperations(final BigDecimal... operands) {
    BigDecimal sum = BigDecimal.ZERO;
    for (final BigDecimal operand : operands) {
      sum = sum.add(operand);
    }
    return sum;
  }
}
