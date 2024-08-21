package br.dev.ferreiras.calculatorWeb.controller;

import br.dev.ferreiras.calculatorWeb.dto.CredentialsRequestDto;
import br.dev.ferreiras.calculatorWeb.dto.LoginRequestDto;
import br.dev.ferreiras.calculatorWeb.dto.LoginResponseDto;
import br.dev.ferreiras.calculatorWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class TokenController {

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


  @PostMapping ("/login")
  public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
    var user = userService.getUsername(loginRequestDto.username());

    if (user.isEmpty() || user.get().isLoginCorrect(loginRequestDto, bCryptPasswordEncoder)) {
      throw new BadCredentialsException("User or Password invalid!!!");
    }

    var expiresIn = 300L;
    var now = Instant.now();
    var claims = JwtClaimsSet.builder()
                             .issuer("calculatorWebBackend")
                             .subject(user.get().getUserId().toString())
                             .issuedAt(now)
                             .expiresAt(now.plusSeconds(expiresIn))
                             .build();

    var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
//    CredentialsRequestDto credentialsDto = userService.validUser(user.get());

    return ResponseEntity.ok(new LoginResponseDto(jwtValue, expiresIn));
  }


}
