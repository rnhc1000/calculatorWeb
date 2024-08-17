package br.dev.ferreiras.calculatorWeb.controller;

import br.dev.ferreiras.calculatorWeb.repository.UserRepository;
import br.dev.ferreiras.calculatorWeb.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

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
  public ResponseEntity<UserResponseDto> findById(@Parameter (description = "user id to be fetched") @PathVariable Long userId) {

    UserResponseDto userResponseDto = userService.getUserById(userId);

    return ResponseEntity.ok(userResponseDto);
  }


}
