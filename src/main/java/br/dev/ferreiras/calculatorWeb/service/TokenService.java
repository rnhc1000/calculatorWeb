package br.dev.ferreiras.calculatorWeb.service;

import br.dev.ferreiras.calculatorWeb.dto.AccessToken;
import br.dev.ferreiras.calculatorWeb.entity.Role;
import br.dev.ferreiras.calculatorWeb.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

public class TokenService {

  @Autowired
  private User user;

  private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

  @Autowired
  private JwtEncoder jwtEncoder;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private UserService userService;

  private AccessToken accessToken;

  public AccessToken validateToken() {

    var user =   new User();
    var scopes = user.getRoles()
                     .stream()
                     .map(Role::getRole)
                     .collect(Collectors.joining(" "));

    logger.info("{} ", scopes);
    var username = user.getUsername();
    var expiresIn = 3600L;
    var now = Instant.now();
    var claims = JwtClaimsSet.builder()
                             .issuer("calculatorWebBackend")
                             .subject(user.getUserId().toString())
                             .issuedAt(now)
                             .expiresAt(now.plusSeconds(expiresIn))
                             .claim("scope", scopes)
                             .claim("username", username)
                             .build();

    String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    accessToken.setToken(jwtValue);
    accessToken.setTimeout(expiresIn);

    return accessToken;
  }
}
