package br.dev.ferreiras.calculatorweb.controller;

import br.dev.ferreiras.calculatorweb.dto.LoadBalanceRequestDto;
import br.dev.ferreiras.calculatorweb.dto.LoadBalanceResponseDto;
import br.dev.ferreiras.calculatorweb.dto.UserResponseDto;
import br.dev.ferreiras.calculatorweb.entity.User;
import br.dev.ferreiras.calculatorweb.service.UserService;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(path = "api/v1")
public class UserController {
private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Operation (summary = "Create a regular user")
  @ApiResponses ({
          @ApiResponse (responseCode = "201", description = "User created", content = @Content (mediaType = "application/json", schema = @Schema (implementation = UserController.class))),
          @ApiResponse (responseCode = "401", description = "Not authorized", content = @Content),
          @ApiResponse (responseCode = "403", description = "Access Denied!", content = @Content),
          @ApiResponse (responseCode = "422", description = "User already exists!", content = @Content)})
  @ResponseStatus
  @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
  @PostMapping ("/users")
  public ResponseEntity<Void> newUser(@RequestBody final UserResponseDto userResponseDto) {

    final var userRole = this.userService.getRole();
    if (this.userService.getUsername(userResponseDto.username()).isPresent()) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
    }
    final var user = new User();
    user.setUsername(userResponseDto.username());
    user.setPassword(this.bCryptPasswordEncoder.encode(userResponseDto.password()));
    user.setBalance(userResponseDto.balance());
    user.setStatus(userResponseDto.status());
    user.setRoles(Set.of(userRole));
    this.userService.saveUser(user);

    return ResponseEntity.ok().build();
  }

  @Operation (summary = "List registered users")
  @ApiResponses ({
          @ApiResponse (responseCode = "200", description = "List of Registered Users", content = @Content (mediaType = "application/json", schema = @Schema (implementation = UserController.class))),
          @ApiResponse (responseCode = "401", description = "Not authorized", content = @Content),
          @ApiResponse (responseCode = "403", description = "Access Denied!", content = @Content),
          @ApiResponse (responseCode = "404", description = "Users not found", content = @Content)})
  @ResponseStatus
  @GetMapping("/users")
  @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
  public ResponseEntity<List<User>> listUsers() {
    final var users = this.userService.findAllUsers();

    return ResponseEntity.ok(users);
  }

  @Operation (summary = "Get a user by its name")
  @ApiResponses ({
          @ApiResponse (responseCode = "200", description = "Got the user requested by its id", content = @Content (mediaType = "application/json", schema = @Schema (implementation = UserController.class))),
          @ApiResponse (responseCode = "401", description = "Not authorized", content = @Content),
          @ApiResponse (responseCode = "403", description = "Access Denied!", content = @Content),
          @ApiResponse (responseCode = "404", description = "Not processable", content = @Content)})
  @ResponseStatus
  @GetMapping ("/users/{username}")
  @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
  public ResponseEntity<UserResponseDto> findById(@Parameter (description = "user to be fetched") @PathVariable final String username) {

    final Optional<User> user = this.userService.getUsername(username);

    return ResponseEntity.ok(new UserResponseDto(
            user.orElseThrow().getUsername(),
            user.orElseThrow().getPassword(),
            user.orElseThrow().getStatus(),
            user.orElseThrow().getBalance())
    );
  }

  @Operation (summary = "Load wallet of a user")
  @ApiResponses ({
          @ApiResponse (responseCode = "201", description = "Wallet loaded", content = @Content (mediaType = "application/json", schema = @Schema (implementation = UserController.class))),
          @ApiResponse (responseCode = "401", description = "Not authorized", content = @Content),
          @ApiResponse (responseCode = "403", description = "Access Denied!", content = @Content),
          @ApiResponse (responseCode = "422", description = "Not Processable!", content = @Content)})
  @ResponseStatus
  @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
  @PostMapping ("/setBalance")
  public ResponseEntity<Integer> loadBalance(@RequestBody final LoadBalanceRequestDto loadBalanceDto) {

    final var useCheck = loadBalanceDto.username();
    UserController.logger.info("username, {}", useCheck);
    if (this.userService.getUsername(useCheck).isEmpty()) {

      UserController.logger.info("User does not exist!");
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    final String username = loadBalanceDto.username();
    final BigDecimal balance = loadBalanceDto.balance();

    final int response = this.userService.updateBalance(username, balance);

    return ResponseEntity.ok(response);
  }

  @Operation (summary = "Get balance of an user")
  @ApiResponses ({
          @ApiResponse (responseCode = "201", description = "Balance returned", content = @Content (mediaType = "application/json", schema = @Schema (implementation = UserController.class))),
          @ApiResponse (responseCode = "401", description = "Not authorized", content = @Content),
          @ApiResponse (responseCode = "422", description = "Not processable!", content = @Content)})
  @ResponseStatus
  @RequestMapping(value = "/balance", method = RequestMethod.POST, produces="application/json", consumes="application/json")
  public ResponseEntity<LoadBalanceResponseDto> getBalance(@RequestBody final LoadBalanceRequestDto loadBalanceRequestDto) {

    final var useCheck = loadBalanceRequestDto.username();
    UserController.logger.info("Username to know the balance, {}", useCheck);

    if (this.userService.getUsername(useCheck).isEmpty()) {

      UserController.logger.info("Username does not exist!");
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    final String username = loadBalanceRequestDto.username();
    final BigDecimal balance = this.userService.getBalance(username);

    return ResponseEntity.ok(new LoadBalanceResponseDto(username, balance));
  }

}