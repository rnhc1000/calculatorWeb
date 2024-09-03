package br.dev.ferreiras.calculatorWeb.dto;

import java.math.BigDecimal;

public record OperationsRequestDto(
        BigDecimal operandOne,
        BigDecimal operandTwo,
        String operator,
        String username) {
}
