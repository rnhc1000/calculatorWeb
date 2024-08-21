package br.dev.ferreiras.calculatorWeb.service;

import br.dev.ferreiras.calculatorWeb.dto.CredentialsRequestDto;
import br.dev.ferreiras.calculatorWeb.entity.User;
import br.dev.ferreiras.calculatorWeb.repository.UserRepository;
import br.dev.ferreiras.calculatorWeb.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService implements IUserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtEncoder jwtEncoder;

//  public UserService(CredentialsRequestDto credentialsRequestDto) {
//  }

  @Override
  public User findById(UUID userId) {
    return userRepository.findById(userId).orElseThrow(
            () -> new ResourceNotFoundException("Resource not found!"));
  }

  @Override
  public User save(User user) {
    return null;
  }

  @Override
  public Iterable<User> findAll() {
    return null;
  }

  @Override
  public Optional<User> getUsername(String username) {
    return userRepository.findByUsername(username);
  }


  //  @Override
//  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    User user = userRepository.findByEmail(username);
//    if (user == null) {
//      throw new UsernameNotFoundException("Email not found");
//    }
//    return (UserDetails) user;
//  }
//
//  protected Optional<User> authenticated(Long id) {
//    try {
//      User user = userRepository.findById(id).orElseThrow();
//      return userRepository.findById(id);
//    }
//    catch (Exception e) {
//      throw new UsernameNotFoundException("Invalid user");
//    }
//  }
//
//  @Transactional(readOnly = true)
//  public UserResponseDto getMe(Long id) {
//    User entity = authenticated(id);
//    return new UserResponseDto(entity);
//  }
//  public CredentialsRequestDto validUser(User user) {
//    var expiresIn = 300L;
//    var now = Instant.now();
//    var claims = JwtClaimsSet.builder()
//                             .issuer("calculatorWebBackend")
//                             .subject(user.getUserId().toString())
//                             .issuedAt(now)
//                             .expiresAt(now.plusSeconds(expiresIn))
//                             .build();
//
//    var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
//
//    return new CredentialsRequestDto(jwtValue, expiresIn);
//  }
}
