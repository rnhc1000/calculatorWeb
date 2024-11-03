package br.dev.ferreiras.calculatorweb.contracts;

import java.math.BigDecimal;

public interface TwoOperandsMathOperations {
    public BigDecimal operation(BigDecimal operandOne, BigDecimal operandTwo);
}
