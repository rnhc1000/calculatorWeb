package br.dev.ferreiras.calculatorWeb.dto;

import java.math.BigDecimal;

public record ResponseRandomDto(String random, BigDecimal balance) {
}
