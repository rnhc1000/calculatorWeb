package br.dev.ferreiras.calculatorweb.service;

import br.dev.ferreiras.calculatorweb.entity.Role;
import br.dev.ferreiras.calculatorweb.entity.User;
import br.dev.ferreiras.calculatorweb.repository.OperationsRepository;
import br.dev.ferreiras.calculatorweb.repository.RoleRepository;
import br.dev.ferreiras.calculatorweb.repository.UserRepository;
import br.dev.ferreiras.calculatorweb.service.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


@Service
public class UserService implements IUserService, UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private JwtEncoder jwtEncoder;

  @Autowired
  private OperationsRepository operationsRepository;


  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  @Override
  public User getUserId(final UUID userId) {
    return this.userRepository.findById(userId).orElseThrow(
            () -> new ResourceNotFoundException("Resource not found!"));
  }

  @Override
  public void saveUser(final User user) {
    this.userRepository.save(user);
  }

  @Override
  public List<User> findAllUsers() {

    return this.userRepository.findAll();
  }

  /**
   *
   * @param username to be checked
   * @return User object if username exists
   */

  @Override
  public Optional<User> getUsername(final String username) {

    return this.userRepository.findByUsername(username);
  }

  @Override
  public Role getRole() {

    return this.roleRepository.findByRole(Role.Roles.ROLE_USER.name());
  }

  @Override
  public int updateBalance(final String username, final BigDecimal balance) {

    return this.userRepository.saveBalance(username, balance);
  }

  @Override
  public BigDecimal getBalance(final String username) {

    return this.userRepository.findByUsernameBalance(username);
  }

  @Override
  public BigDecimal getOperationCostById(final Long operationId) {

    return this.operationsRepository.findOperationsCostById(operationId);
  }

  public BigDecimal getOperationCostByOperation(final String operation) {

    return this.operationsRepository.findOperationsCostByOperation(operation);
  }

  public String authenticated() throws Exception {

    try {
      final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      final var User = this.userRepository.findById(UUID.fromString(authentication.getName()));
      final String currentUserName = User.orElseThrow().getUsername();
      UserService.logger.info("CurrentUsername -> {}", currentUserName);
      try {
        return currentUserName;
      } catch (final Exception e) {
        throw new NoSuchElementException("Resource not Found!");
      }

    } catch (final NoSuchElementException e) {
      throw new RuntimeException(e);
    }
  }

//    @Transactional (readOnly = true)
//  public UserDto getMe() {
//    Optional<User> entity = authenticated();
//    return new UserDto(entity);
//  }

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    final Optional<User> user = this.userRepository.findByUsername(username);
    if (user.isEmpty()) {
      throw new UsernameNotFoundException("User not found");
    }
    return new UserDetails() {
      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
      }

      @Override
      public String getPassword() {
        return new User().getPassword();
      }

      @Override
      public String getUsername() {
        return new User().getUsername();
      }
    };
  }
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

