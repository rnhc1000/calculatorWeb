package br.dev.ferreiras.calculatorWeb.dto;

import lombok.*;

/** Access Token object, type definition;
 * @author Ricardo Ferreira
 * @version 1.1.0916202401
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccessToken {
  String token;
  Long timeout;
}
