package br.dev.ferreiras.calculatorweb.dto;

import java.math.BigDecimal;

public record RecordItemsDto(
        Long recordId,
        String username, BigDecimal operandOne,
        BigDecimal operandTwo, String operator, String result,
        BigDecimal cost, java.time.Instant createdAt, boolean deleted) {
}
