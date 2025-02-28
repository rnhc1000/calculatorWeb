package br.dev.ferreiras.calculatorweb.controller;

import br.dev.ferreiras.calculatorweb.dto.LoginRequestDto;
import br.dev.ferreiras.calculatorweb.dto.LoginResponseDto;
import br.dev.ferreiras.calculatorweb.entity.User;
import br.dev.ferreiras.calculatorweb.service.TokenService;
import br.dev.ferreiras.calculatorweb.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1")
public class TokenController {

  private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

  private final UserService userService;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  private final TokenService tokenService;

  public TokenController(
      final BCryptPasswordEncoder bCryptPasswordEncoder,
      final UserService userService, final TokenService tokenService) {

    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.userService = userService;
    this.tokenService = tokenService;
  }

  @Operation(
      summary = "Authenticate a user",
      description = "Authenticate a user and return an access token and its expiration time",
      responses = {
          @ApiResponse(responseCode = "201", description = "Access Token created!",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = LoginResponseDto.class))),
          @ApiResponse(responseCode = "403", description = "Not Authorized!", content = @Content),
          @ApiResponse(responseCode = "404", description = "Resource Not Found!", content = @Content),
      })
  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(@RequestBody final LoginRequestDto loginRequestDto) {

    final Optional<User> user = this.userService.getUsername(loginRequestDto.username());

    if (user.isEmpty() || !user.get().isLoginCorrect(loginRequestDto, this.bCryptPasswordEncoder)) {

      throw new BadCredentialsException("Try again with good credentials!");

    } else {

      final var accessToken = this.tokenService.generateToken(((user.get().getUsername())));
      TokenController.logger.info("Access Token-> , {}", accessToken);
      final var jwtValue = accessToken.getToken();
      final var expiresIn = accessToken.getTimeout();

      final URI uri = ServletUriComponentsBuilder
          .fromCurrentRequest()
          .path("/{username}")
          .buildAndExpand(loginRequestDto.username())
          .toUri();

      return ResponseEntity.created(uri).body(new LoginResponseDto(jwtValue, expiresIn));
    }
  }
}
