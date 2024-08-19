package br.dev.ferreiras.calculatorWeb.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RandomApiRequestDto {
    private String apiKey;
    private String jsonrpc;
    private String method;
    private String id;
//    "id", UUID.randomUUID().toString());
    private String n;
    private String length;
    private String characters;
    private boolean replacement;

  @Override
  public String toString() {
    return "RandomApiRequestDto{" +
           "apiKey='" + apiKey + '\'' +
           ", jsonrpc='" + jsonrpc + '\'' +
           ", method='" + method + '\'' +
           ", id='" + id + '\'' +
           ", n='" + n + '\'' +
           ", length='" + length + '\'' +
           ", characters='" + characters + '\'' +
           ", replacement=" + replacement +
           '}';
  }
}
