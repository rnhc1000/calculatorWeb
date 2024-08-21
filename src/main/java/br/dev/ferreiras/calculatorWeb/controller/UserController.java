package br.dev.ferreiras.calculatorWeb.controller;

import br.dev.ferreiras.calculatorWeb.dto.RandomApiRequestDto;
import br.dev.ferreiras.calculatorWeb.dto.RandomApiResponseDto;
import br.dev.ferreiras.calculatorWeb.dto.UserResponseDto;
import br.dev.ferreiras.calculatorWeb.entity.User;
import br.dev.ferreiras.calculatorWeb.service.RandomService;
import br.dev.ferreiras.calculatorWeb.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class UserController {
private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  @Autowired
  private RandomService randomService;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Operation (summary = "Create a regular user")
  @ApiResponses (value = {
          @ApiResponse (responseCode = "201", description = "User created",
                  content = {@Content (mediaType = "application/json",
                          schema = @Schema (implementation = UserController.class))}),
          @ApiResponse (responseCode = "401", description = "Not authorized",
                  content = @Content),
          @ApiResponse (responseCode = "422", description = "User already exists!",
                  content = @Content)})
  @ResponseStatus
  @PostMapping ("/users")
  public ResponseEntity<Void> newUser(@RequestBody UserResponseDto userResponseDto) {

    var userRole = userService.getRole();
    if (userService.getUsername(userResponseDto.username()).isPresent()) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
    }
    var user = new User();
    user.setUsername(userResponseDto.username());
    user.setPassword(bCryptPasswordEncoder.encode(userResponseDto.password()));
    user.setStatus(userResponseDto.status());
    user.setRoles(Set.of(userRole));
    userService.saveUser(user);

    return ResponseEntity.ok().build();
  }

  @GetMapping("/users")
  @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
  public ResponseEntity<List<User>> listUsers() {
    var users = userService.findAllUsers();

    return ResponseEntity.ok(users);
  }

  @Operation (summary = "Get a user by its name")
  @ApiResponses (value = {
          @ApiResponse (responseCode = "200", description = "Got the user requested by its id",
                  content = {@Content (mediaType = "application/json",
                          schema = @Schema (implementation = UserController.class))}),
          @ApiResponse (responseCode = "401", description = "Not authorized",
                  content = @Content),
          @ApiResponse (responseCode = "404", description = "User not found",
                  content = @Content)})
  @ResponseStatus
  @GetMapping (value = "/users/{username}")
  @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
  public ResponseEntity<UserResponseDto> findById(@Parameter (description = "user to be fetched") @PathVariable String username) {
    Optional<User> user = userService.getUsername(username);
    return ResponseEntity.ok(new UserResponseDto(user.get().getUsername(), user.get().getPassword(), user.get().getStatus()));

  }

  @Operation (summary = "Get a random string")
  @ApiResponses (value = {
          @ApiResponse (responseCode = "200", description = "Get a random string through random.org",
                  content = {@Content (mediaType = "application/json"
//                          schema = @Schema (implementation = UserController.class)
                  )}),
          @ApiResponse (responseCode = "401", description = "Not Authorized",
                  content = @Content),
          @ApiResponse (responseCode = "404", description = "endpoint not found",
                  content = @Content),
          @ApiResponse (responseCode = "415", description = "media not supported",
                  content = @Content)
  })
  @ResponseStatus
  @PostMapping (value = "/random", consumes = {"application/json"})
  @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
  public ResponseEntity<String> getRandomString(@RequestBody String randomApiRequestDto)  {

    try {
      randomApiRequestDto = randomService.prepareRequestBody();
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    logger.info("{}", randomApiRequestDto);
    String list  = randomService.makeApiRequest();

    return ResponseEntity.ok(list);
  }
}
