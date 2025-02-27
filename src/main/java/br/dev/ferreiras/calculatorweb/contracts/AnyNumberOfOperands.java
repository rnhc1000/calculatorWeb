package br.dev.ferreiras.calculatorweb.contracts;

import java.math.BigDecimal;

public interface AnyNumberOfOperands {
  public BigDecimal mathOperations(BigDecimal... operations);
}
