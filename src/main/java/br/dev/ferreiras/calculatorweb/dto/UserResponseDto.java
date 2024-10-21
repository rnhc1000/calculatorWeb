package br.dev.ferreiras.calculatorweb.dto;

import java.math.BigDecimal;

public record UserResponseDto(String username, String password, String status, BigDecimal balance) {
  public UserResponseDto(final String username, final String status) {
    this(username, "", status, BigDecimal.ZERO);
  }
}
