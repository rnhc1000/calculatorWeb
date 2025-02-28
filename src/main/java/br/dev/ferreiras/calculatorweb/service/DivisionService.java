package br.dev.ferreiras.calculatorweb.service;

import br.dev.ferreiras.calculatorweb.contracts.AnyNumberOfOperands;
import br.dev.ferreiras.calculatorweb.service.exceptions.InvalidMathRequestException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DivisionService implements AnyNumberOfOperands {
    /**
     * @param operands > 0 || < 0 || = 0
     * @return operandOne / operandTwo
     * @throws InvalidMathRequestException no / by zero
     */
    @Override
    public BigDecimal mathOperations(final BigDecimal ...operands) {

        BigDecimal result = BigDecimal.ONE;

        try {

            for (BigDecimal operand : operands) {

                result = result.divide(operand, 4, RoundingMode.CEILING);
            }

        } catch (final ArithmeticException ex) {

            throw new InvalidMathRequestException("Only positive numbers!");

        }

        return result;
    }
}
