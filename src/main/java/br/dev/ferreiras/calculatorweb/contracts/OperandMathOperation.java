/*
 * Copyright (c) 2025.
 * ricardo@ferreiras.dev.br
 */

package br.dev.ferreiras.calculatorweb.contracts;

import java.math.BigDecimal;

public interface OperandMathOperation {
    public BigDecimal operation(BigDecimal ...operand);
}
