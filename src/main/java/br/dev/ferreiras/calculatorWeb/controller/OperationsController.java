package br.dev.ferreiras.calculatorWeb.controller;

import br.dev.ferreiras.calculatorWeb.dto.*;
import br.dev.ferreiras.calculatorWeb.entity.User;
import br.dev.ferreiras.calculatorWeb.service.OperationsService;
import br.dev.ferreiras.calculatorWeb.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping (path = "api/v1")
public class OperationsController {

  @Autowired
  private OperationsService operationsService;

  @Autowired
  private UserService userService;

  @Operation (summary = "Given one or two operands and the operator return a value")
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
            operationsRequestDto.operator(),
            operationsRequestDto.username()
    );

    return ResponseEntity.ok(new OperationsResponseDto(result));
  }

  @Operation (summary = "Given username and operator return a random string")
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

    String result = operationsService.executeOperations(

            requestRandomDto.username(),
            requestRandomDto.operator()
    );

    return ResponseEntity.ok(new ResponseRandomDto(result));
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
  public ResponseEntity<List<br.dev.ferreiras.calculatorWeb.entity.Operation>> getAllOperators() {

      var operators = operationsService.getOperators();

      return ResponseEntity.ok(operators);
  }
}
