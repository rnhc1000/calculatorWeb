package br.dev.ferreiras.calculatorweb.service;

import br.dev.ferreiras.calculatorweb.contracts.TwoOperandsMathOperations;

import java.math.BigDecimal;

public class Multiplication implements TwoOperandsMathOperations {
    /**
     * @param operandOne first operand
     * @param operandTwo second operand
     * @return product of two operands
     */
    @Override
    public BigDecimal operation(final BigDecimal operandOne, final BigDecimal operandTwo) {
        return operandOne.multiply(operandTwo);
    }
}
