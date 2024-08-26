package br.dev.ferreiras.calculatorWeb.dto;

import java.math.BigDecimal;

public record LoadBalanceResponseDto(String username, BigDecimal balance) {
}
