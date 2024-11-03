package br.dev.ferreiras.calculatorweb.service;

import br.dev.ferreiras.calculatorweb.contracts.OneOperandMathOperation;
import br.dev.ferreiras.calculatorweb.service.exceptions.InvalidMathRequestException;

import java.math.BigDecimal;
import java.math.MathContext;

public class SquareRoot implements OneOperandMathOperation {

    /**
     * @param operandOne must be > 0
     * @return square root of operandOne
     * @throws InvalidMathRequestException no negative numbers supported
     */
    @Override
    public BigDecimal operation(final BigDecimal operandOne) {

        final MathContext mathContext = new MathContext(4);
        try {

           return operandOne.sqrt(mathContext);

        } catch (final ArithmeticException ex) {

            throw new InvalidMathRequestException("Only positive numbers");

        }
    }
}
