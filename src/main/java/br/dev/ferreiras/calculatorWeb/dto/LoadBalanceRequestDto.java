package br.dev.ferreiras.calculatorWeb.dto;

import java.math.BigDecimal;

public record LoadBalanceRequestDto(String username, BigDecimal balance){
}
