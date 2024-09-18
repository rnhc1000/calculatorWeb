package br.dev.ferreiras.calculatorWeb.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccessToken {
  String token;
  Long timeout;
}
