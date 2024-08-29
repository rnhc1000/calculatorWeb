package br.dev.ferreiras.calculatorWeb.dto;

import java.util.List;

public record RecordsDto(List<RecordItemsDto> records,
                         int page,
                         int size,
                         int totalPages,
                         long totalRecords){
}
