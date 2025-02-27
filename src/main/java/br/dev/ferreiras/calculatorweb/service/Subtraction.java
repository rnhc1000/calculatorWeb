package br.dev.ferreiras.calculatorweb.service;

import br.dev.ferreiras.calculatorweb.contracts.AnyNumberOfOperands;

import java.math.BigDecimal;

public class Subtraction implements AnyNumberOfOperands {
    /**
     * @param operands ...
     * @return subtraction of two operands
     */
    @Override
    public BigDecimal mathOperations(final BigDecimal ...operands) {

        BigDecimal result =  BigDecimal.ZERO;

        for (BigDecimal operand : operands) {
            result = result.subtract(operand);
        }

        return result;
    }
}
