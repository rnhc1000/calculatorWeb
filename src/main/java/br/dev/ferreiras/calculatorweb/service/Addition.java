package br.dev.ferreiras.calculatorweb.service;

import br.dev.ferreiras.calculatorweb.contracts.TwoOperandsMathOperations;

import java.math.BigDecimal;

public class Addition implements TwoOperandsMathOperations {

    /**
     * @param operandOne first operand
     * @param operandTwo second operand
     * @return sum of two operands
     */
    @Override
    public BigDecimal operation( BigDecimal operandOne, BigDecimal operandTwo) {
        operandOne = (null == operandOne ? BigDecimal.ZERO : operandOne);
        operandTwo = (null == operandTwo ? BigDecimal.ZERO : operandTwo);

        return operandOne.add(operandTwo);
    }
}
