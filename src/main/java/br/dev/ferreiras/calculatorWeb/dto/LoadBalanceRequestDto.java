package br.dev.ferreiras.calculatorWeb.dto;

import java.math.BigDecimal;

public record LoadBalanceRequestDto(String username,String password, BigDecimal balance){
}
