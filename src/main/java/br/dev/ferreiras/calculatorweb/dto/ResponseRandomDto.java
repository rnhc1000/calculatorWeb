package br.dev.ferreiras.calculatorweb.dto;

import java.math.BigDecimal;

public record ResponseRandomDto(String random, BigDecimal balance) {
}
