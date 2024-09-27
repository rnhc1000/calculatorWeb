package br.dev.ferreiras.calculatorweb.controller;

import br.dev.ferreiras.calculatorweb.dto.*;
import br.dev.ferreiras.calculatorweb.service.OperationsService;
import br.dev.ferreiras.calculatorweb.service.UserService;
import br.dev.ferreiras.calculatorweb.service.exceptions.ForbiddenException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
  @ApiResponses (value = {
          @ApiResponse (responseCode = "200", description = "Got the result",
                  content = {@Content (mediaType = "application/json",
                          schema = @Schema (implementation = OperationsController.class))}),
          @ApiResponse (responseCode = "401", description = "Not authorized!",
                  content = @Content),
          @ApiResponse (responseCode = "422", description = "Operation not allowed!",
                  content = @Content),
  })
  @ResponseStatus
  @PostMapping (value = "/operations")
  public ResponseEntity<OperationsResponseDto> getResults(@RequestBody OperationsRequestDto operationsRequestDto) {

    OperationsController.logger.info("Received data to do maths...");
    try {
      final OperationsResponseDto operationsResult = this.operationsService.executeOperations(
              operationsRequestDto.operandOne(),
              operationsRequestDto.operandTwo(),
              operationsRequestDto.operator(),
              operationsRequestDto.username()
      );

      OperationsController.logger.info("Maths done!..");

      return ResponseEntity.ok(new OperationsResponseDto(operationsResult.result(), operationsResult.balance()));

    } catch (Exception ex) {
      OperationsController.logger.info("Valid operations only! ...");
      throw new ForbiddenException("Arithmetic Exception");
    }
  }

  @Operation (summary = "Given username and operator return a random string, and associated balance")
  @ApiResponses (value = {
          @ApiResponse (responseCode = "200", description = "Got the result",
                  content = {@Content (mediaType = "application/json",
                          schema = @Schema (implementation = OperationsController.class))}),
          @ApiResponse (responseCode = "401", description = "Not authorized",
                  content = @Content),
  })
  @ResponseStatus
  @PostMapping (value = "/randomize")
  public ResponseEntity<ResponseRandomDto> getRandomStrings(@RequestBody RequestRandomDto requestRandomDto) {

    final ResponseRandomDto operationsResult = this.operationsService.executeOperations(

            requestRandomDto.username(),
            requestRandomDto.operator()
    );

    return ResponseEntity.ok(new ResponseRandomDto(operationsResult.random(), operationsResult.balance()));
  }

  @Operation (summary = "Return registered operators")
  @ApiResponses (value = {
          @ApiResponse (responseCode = "200", description = "Got the results",
                  content = {@Content (mediaType = "application/json",
                          schema = @Schema (implementation = OperationsController.class))}),
          @ApiResponse (responseCode = "401", description = "Not authorized",
                  content = @Content),
  })
  @ResponseStatus
  @GetMapping(value = "/operators")
  public ResponseEntity<List<Object>> getAllOperators() {

      final var operators = this.operationsService.getOperationsCost();

      return ResponseEntity.ok(operators);
  }
}
