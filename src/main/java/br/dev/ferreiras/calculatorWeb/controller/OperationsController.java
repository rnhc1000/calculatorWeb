package br.dev.ferreiras.calculatorWeb.controller;

import br.dev.ferreiras.calculatorWeb.dto.OperationsRequestDto;
import br.dev.ferreiras.calculatorWeb.dto.OperationsResponseDto;
import br.dev.ferreiras.calculatorWeb.service.OperationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class OperationsController {

  @Autowired
  private OperationsService operationsService;

  @Operation (summary = "Given two operands and one operator return a value")
  @ApiResponses (value = {
          @ApiResponse (responseCode = "200", description = "Got the result",
                  content = {@Content (mediaType = "application/json",
                          schema = @Schema (implementation = OperationsController.class))}),
          @ApiResponse (responseCode = "401", description = "Not authorized",
                  content = @Content),
  })
  @ResponseStatus
  @PostMapping (value = "/operations")
  public ResponseEntity<OperationsResponseDto> getResults(@RequestBody OperationsRequestDto operationsRequestDto) {

    BigDecimal result = operationsService.executeOperations(
            operationsRequestDto.operandOne(),
            operationsRequestDto.operandTwo(),
            operationsRequestDto.operator());

    return ResponseEntity.ok(new OperationsResponseDto(result));
  }
}
