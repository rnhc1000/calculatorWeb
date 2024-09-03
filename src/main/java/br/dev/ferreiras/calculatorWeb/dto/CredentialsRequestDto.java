package br.dev.ferreiras.calculatorWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CredentialsRequestDto {
  private String accessToken;
  private Long expiresIn;
}
