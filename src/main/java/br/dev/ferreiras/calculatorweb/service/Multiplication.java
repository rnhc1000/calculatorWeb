package br.dev.ferreiras.calculatorweb.service;

import br.dev.ferreiras.calculatorweb.contracts.AnyNumberOfOperands;

import java.math.BigDecimal;

public class Multiplication implements AnyNumberOfOperands {
    /**
     * @param operands first operand, second operand
     * @return product of two operands
     */
    @Override
    public BigDecimal mathOperations(final BigDecimal ...operands) {

        BigDecimal result = BigDecimal.ONE;
        for (BigDecimal operand : operands) {
            result = result.multiply(operand);
        }

        return result;
    }
}
