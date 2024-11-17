package br.dev.ferreiras.calculatorweb.contracts;

import java.math.BigDecimal;

public interface AnyNumberOfOperations {
  public BigDecimal mathOperations(BigDecimal... operations);
}
