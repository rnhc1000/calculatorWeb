package br.dev.ferreiras.calculatorWeb.controller;

import br.dev.ferreiras.calculatorWeb.dto.LoginRequestDto;
import br.dev.ferreiras.calculatorWeb.dto.LoginResponseDto;
import br.dev.ferreiras.calculatorWeb.entity.Role;
import br.dev.ferreiras.calculatorWeb.service.TokenService;
import br.dev.ferreiras.calculatorWeb.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1")
public class TokenController {

  private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

  private final JwtEncoder jwtEncoder;

  private final UserService userService;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;


  public TokenController(JwtEncoder jwtEncoder,
                         BCryptPasswordEncoder bCryptPasswordEncoder,
                         UserService userService) {
    this.jwtEncoder = jwtEncoder;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.userService = userService;
  }

  private static String apply(Role Role) {
    return Role.getClass().getName();
  }

  @Operation (summary = "Authenticate a user and return an access token and its expiration time")
  @ApiResponses (value = {
          @ApiResponse (responseCode = "201", description = "Login successful",
                  content = {@Content (mediaType = "application/json",
                          schema = @Schema (implementation = TokenController.class))}),
          @ApiResponse (responseCode = "401", description = "Not authorized",
                  content = @Content),
         })
  @ResponseStatus
  @RequestMapping(value = "/login", method = RequestMethod.POST, produces="application/json", consumes="application/json")
  public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
    var user = userService.getUsername(loginRequestDto.username());

    if (user.isEmpty() || !user.get().isLoginCorrect(loginRequestDto, bCryptPasswordEncoder)) {
      logger.info("Password mismatch....");
      throw new BadCredentialsException("User or Password Invalid!!!");
    }

    var scopes = user.get().getRoles()
                     .stream()
                     .map(Role::getRole)
                     .collect(Collectors.joining(" "));

    logger.info("{} ", scopes);
    var username = user.get().getUsername();
    var expiresIn = 300L;
    var now = Instant.now();
    var claims = JwtClaimsSet.builder()
                             .issuer("calculatorWebBackend")
                             .subject(user.get().getUserId().toString())
                             .issuedAt(now)
                             .expiresAt(now.plusSeconds(expiresIn))
                             .claim("scope", scopes)
                             .claim("username", username)
                             .build();

    var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    return ResponseEntity.ok(new LoginResponseDto(jwtValue, expiresIn));
  }
}
