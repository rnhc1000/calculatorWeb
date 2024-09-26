package br.dev.ferreiras.calculatorweb.dto;

import java.math.BigDecimal;

public record LoadBalanceResponseDto(String username, BigDecimal balance) {
}
