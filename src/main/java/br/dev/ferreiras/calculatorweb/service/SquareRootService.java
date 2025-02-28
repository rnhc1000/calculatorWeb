package br.dev.ferreiras.calculatorweb.service;

import br.dev.ferreiras.calculatorweb.contracts.AnyNumberOfOperands;
import br.dev.ferreiras.calculatorweb.service.exceptions.InvalidMathRequestException;

import java.math.BigDecimal;
import java.math.MathContext;

public class SquareRootService implements AnyNumberOfOperands {

    /**
     * @param operands must be > 0
     * @return square root of operandOne
     * @throws InvalidMathRequestException no negative numbers supported
     */
    @Override
    public BigDecimal mathOperations(final BigDecimal ...operands) {

        final MathContext mathContext = new MathContext(4);
        BigDecimal result = BigDecimal.ONE;
        try {

            for (BigDecimal operand: operands) {
                result = operand.sqrt(mathContext);
            }

        } catch (final ArithmeticException ex) {

            throw new InvalidMathRequestException("Only positive numbers");

        }

        return result;
    }
}
