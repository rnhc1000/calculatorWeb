package br.dev.ferreiras.calculatorweb.service;

import br.dev.ferreiras.calculatorweb.contracts.TwoOperandsMathOperations;
import br.dev.ferreiras.calculatorweb.service.exceptions.InvalidMathRequestException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Division implements TwoOperandsMathOperations {
    /**
     * @param operandOne > 0 || < 0 || = 0
     * @param operandTwo > 0
     * @return operandOne / operandTwo
     * @throws InvalidMathRequestException no / by zero
     */
    @Override
    public BigDecimal operation(final BigDecimal operandOne, final BigDecimal operandTwo) {

        BigDecimal division = BigDecimal.ZERO;

        try {

            division = operandOne.divide(operandTwo, 4, RoundingMode.CEILING);

        } catch (final ArithmeticException ex) {

            throw new InvalidMathRequestException("/ by zero");

        }

        return division;
    }
}
