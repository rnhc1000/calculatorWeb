package br.dev.ferreiras.calculatorWeb.dto;

import java.math.BigDecimal;
import java.util.Set;

public record UserResponseDto (String username, String password, String status, BigDecimal balance){
}
