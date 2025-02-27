package br.dev.ferreiras.calculatorweb.dto;

import java.time.Instant;

public record ErrorResponseDto(Instant timeStamp,  int status, String message, String path) {
}
