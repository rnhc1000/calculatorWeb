package br.dev.ferreiras.calculatorweb.controller;

import br.dev.ferreiras.calculatorweb.dto.OperationsRequestDto;
import br.dev.ferreiras.calculatorweb.dto.OperationsResponseDto;
import br.dev.ferreiras.calculatorweb.dto.RequestRandomDto;
import br.dev.ferreiras.calculatorweb.dto.ResponseRandomDto;
import br.dev.ferreiras.calculatorweb.service.OperationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping (path = "api/v1")
public class OperationsController {

  private final OperationsService operationsService;

  public OperationsController(final OperationsService operationsService) {
    this.operationsService = operationsService;
  }

  private static final Logger logger = LoggerFactory.getLogger(OperationsController.class);

  @Operation (summary = "Given one or two operands and an operator, returns the result and balance available")
  @ApiResponses ({
          @ApiResponse (responseCode = "200", description = "Got the result", content = @Content (mediaType = "application/json",
                  schema = @Schema (implementation = OperationsController.class))),
          @ApiResponse (responseCode = "401", description = "Not authorized!", content = @Content),
          @ApiResponse (responseCode = "422", description = "Operation not allowed!", content = @Content),
  })
  @ResponseStatus(value = HttpStatus.CREATED)
  @PostMapping ("/operations")
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

  @Operation (summary = "Given username and operator return a random string, and associated balance")
  @ApiResponses ({
          @ApiResponse (responseCode = "200", description = "Got the result", content = @Content (mediaType = "application/json", schema = @Schema (implementation = OperationsController.class))),
          @ApiResponse (responseCode = "401", description = "Not authorized", content = @Content),
  })
  @ResponseStatus(value = HttpStatus.CREATED)
  @PostMapping ("/randomize")
  public ResponseEntity<ResponseRandomDto> getRandomStrings(@RequestBody final RequestRandomDto requestRandomDto) {

    final ResponseRandomDto operationsResult = this.operationsService.executeOperations(
            requestRandomDto.username(),
            requestRandomDto.operator()
    );

    return ResponseEntity.ok(new ResponseRandomDto(operationsResult.random(), operationsResult.balance()));
  }

  @Operation (summary = "Return registered operators")
  @ApiResponses ({
          @ApiResponse (responseCode = "200", description = "Got the results", content = @Content (mediaType = "application/json", schema = @Schema (implementation = OperationsController.class))),
          @ApiResponse (responseCode = "401", description = "Not authorized", content = @Content),
  })
  @ResponseStatus(value = HttpStatus.OK)
  @GetMapping ("/operators")
  public ResponseEntity<List<Object>> getAllOperators() {
    final var operators = this.operationsService.getOperationsCost();

    return ResponseEntity.ok(operators);
  }
}
