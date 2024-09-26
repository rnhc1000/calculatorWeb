package br.dev.ferreiras.calculatorweb.dto;

import java.math.BigDecimal;

public record OperatorsDto(Long id, BigDecimal cost, String operator) {
}
