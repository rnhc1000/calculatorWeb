package br.dev.ferreiras.calculatorWeb.controller;

import br.dev.ferreiras.calculatorWeb.dto.RandomApiRequestDto;
import br.dev.ferreiras.calculatorWeb.dto.RandomApiResponseDto;
import br.dev.ferreiras.calculatorWeb.service.RandomService;
import br.dev.ferreiras.calculatorWeb.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private RandomService randomService;

  @Operation (summary = "Get a user by its id")
  @ApiResponses (value = {
          @ApiResponse (responseCode = "200", description = "Got the user requested by its id",
                  content = {@Content (mediaType = "application/json",
                          schema = @Schema (implementation = UserController.class))}),
          @ApiResponse (responseCode = "400", description = "Invalid id supplied",
                  content = @Content),
          @ApiResponse (responseCode = "404", description = "User not found",
                  content = @Content)})
  @ResponseStatus
  @GetMapping (value = "/users/{userId}")
  public ResponseEntity<String> findById(@Parameter (description = "user id to be fetched") @PathVariable Long userId) {

    String userResponseDto = userService.getUserById(userId);

    return ResponseEntity.ok(userResponseDto);
  }

  @Operation (summary = "Get a random string")
  @ApiResponses (value = {
          @ApiResponse (responseCode = "200", description = "Get a random string through random.org",
                  content = {@Content (mediaType = "application/json"
//                          schema = @Schema (implementation = UserController.class)
                  )}),
          @ApiResponse (responseCode = "400", description = "Invalid string",
                  content = @Content),
          @ApiResponse (responseCode = "404", description = "endpoint not found",
                  content = @Content),
          @ApiResponse (responseCode = "415", description = "media not supported",
                  content = @Content)
  })

  @ResponseStatus
  @PostMapping (value = "/random", consumes = {"application/xml", "application/json"})
  public ResponseEntity getRandomString(@RequestBody RandomService randomService )  {

    RandomApiResponseDto randomResponse = null;
    try {
      randomResponse = randomService.makeApiRequest();
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                                                             .path("/")
                                                             .buildAndExpand(randomResponse.getData())
                                                             .toUri())
                         .body(randomResponse);

  }
}
