package br.dev.ferreiras.calculatorweb.dto;

import java.time.Instant;

public record ErrorResponseDto(int httpCode, String message, Instant timeStamp) {
}
