package br.dev.ferreiras.calculatorWeb.dto;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RandomApiResponseDto {
  private JsonObject data;
}
