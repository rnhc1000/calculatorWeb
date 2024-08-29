package br.dev.ferreiras.calculatorWeb.dto;

import java.math.BigDecimal;

public record OperatorsDto(Long id, BigDecimal cost, String operator) {
}
