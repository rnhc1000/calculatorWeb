package br.dev.ferreiras.calculatorweb.dto;

import java.util.List;

public record RecordsDto(List<RecordItemsDto> records,
                         int page,
                         int size,
                         int totalPages,
                         long totalRecords){
}
