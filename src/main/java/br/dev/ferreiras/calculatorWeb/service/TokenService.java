package br.dev.ferreiras.calculatorWeb.service;

import br.dev.ferreiras.calculatorWeb.dto.AccessToken;
import br.dev.ferreiras.calculatorWeb.entity.Role;
import br.dev.ferreiras.calculatorWeb.entity.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TokenService {
  private final JwtEncoder jwtEncoder;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final UserService userService;

  public TokenService(
          final JwtEncoder jwtEncoder, final BCryptPasswordEncoder bCryptPasswordEncoder,
          final UserService userService) {
    this.jwtEncoder = jwtEncoder;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.userService = userService;
  }

  private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

  /**
   * @param username  as oan object
   * @return accessToken object
   */
  public AccessToken generateToken(final String username) {
    TokenService.logger.info("Username -> {}", username);
    final Optional<User> user = this.userService.getUsername(username);
    final var scopes = user.get().getRoles()
                           .stream()
                           .map(Role::getRole)
                           .collect(Collectors.joining(" "));
    TokenService.logger.info("scopes -> {}", scopes);
    final var balance = this.userService.getBalance(username);
    final var expiresIn = 3600L;
    final var now = Instant.now();
    final var claims = JwtClaimsSet.builder()
                                   .issuer("calculatorWebBackend")
                                   .subject(user.get().getUserId().toString())
                                   .issuedAt(now)
                                   .expiresAt(now.plusSeconds(expiresIn))
                                   .claim("scope", scopes)
                                   .claim("username", username)
                                   .claim("balance", balance)
                                   .build();

    final String jwtValue = this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    TokenService.logger.info(jwtValue);
    return new AccessToken(jwtValue, expiresIn);
  }

}
