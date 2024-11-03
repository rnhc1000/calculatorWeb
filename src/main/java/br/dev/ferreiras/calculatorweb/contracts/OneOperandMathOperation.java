package br.dev.ferreiras.calculatorweb.contracts;

import java.math.BigDecimal;

public interface OneOperandMathOperation {
    public BigDecimal operation(BigDecimal operandOne);
}
