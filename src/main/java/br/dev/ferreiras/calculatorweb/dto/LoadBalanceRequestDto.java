package br.dev.ferreiras.calculatorweb.dto;

import java.math.BigDecimal;

public record LoadBalanceRequestDto(String username, BigDecimal balance){
}
