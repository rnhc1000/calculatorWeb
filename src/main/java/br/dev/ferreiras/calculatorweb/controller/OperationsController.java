package br.dev.ferreiras.calculatorweb.controller;

import br.dev.ferreiras.calculatorweb.dto.*;
import br.dev.ferreiras.calculatorweb.service.OperationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
public class OperationsController {

  private final OperationsService operationsService;

  public OperationsController(final OperationsService operationsService) {
    this.operationsService = operationsService;
  }

  private static final Logger logger = LoggerFactory.getLogger(OperationsController.class);

  @Operation(
      summary = "Given one or two operands and an operator, do the maths",
      description = "Given one or two operands and an operator, returns the result and balance available",
      responses = {
          @ApiResponse(responseCode = "200", description = "Got the result", content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = OperationsResponseDto.class))),
          @ApiResponse(responseCode = "401", description = "Not authorized!", content = @Content),
          @ApiResponse(responseCode = "422", description = "Operation not allowed!", content = @Content),
      })
  @PostMapping("/operations")
  public ResponseEntity<OperationsResponseDto> getResults(@RequestBody final OperationsRequestDto operationsRequestDto) {

    OperationsController.logger.info("Received data to do maths...");
    final OperationsResponseDto operationsResult = this.operationsService.executeOperations(
        operationsRequestDto.operandOne(),
        operationsRequestDto.operandTwo(),
        operationsRequestDto.operator(),
        operationsRequestDto.username()
    );

    OperationsController.logger.info("Maths done!..");

    return ResponseEntity.ok(new OperationsResponseDto(operationsResult.result(), operationsResult.balance()));
  }

  @Operation(
      summary = "Given username and operator return a random string",
      description = "Given username and operator return a random string, and associated balance from random.org",
      responses = {
          @ApiResponse(responseCode = "200", description = "Got the result",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ResponseRandomDto.class))),
          @ApiResponse(responseCode = "401", description = "Not authorized", content = @Content),
      })
  @PostMapping("/randomize")
  public ResponseEntity<ResponseRandomDto> getRandomStrings(@RequestBody final RequestRandomDto requestRandomDto) {

    final ResponseRandomDto operationsResult = this.operationsService.executeOperations(
        requestRandomDto.username(),
        requestRandomDto.operator()
    );

    return ResponseEntity.ok(new ResponseRandomDto(operationsResult.random(), operationsResult.balance()));
  }

  @Operation(summary = "Return registered operators",
      description = "Return all operators available, Addition, Subtraction, Division, Multiplication, " +
                    "SquareRoot and Random String",
      responses = {
          @ApiResponse(responseCode = "200", description = "Got the results",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = OperatorsDto.class))),
          @ApiResponse(responseCode = "401", description = "Not authorized", content = @Content)
      })
  @GetMapping("/operators")
  public ResponseEntity<List<Object>> getAllOperators() {
    final var operators = this.operationsService.getOperationsCost();

    return ResponseEntity.ok(operators);
  }
}
