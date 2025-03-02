package br.dev.ferreiras.calculatorweb.service;

import br.dev.ferreiras.calculatorweb.dto.LoadBalanceResponseDto;
import br.dev.ferreiras.calculatorweb.dto.UserRequestDto;
import br.dev.ferreiras.calculatorweb.dto.UserResponseDto;
import br.dev.ferreiras.calculatorweb.entity.Role;
import br.dev.ferreiras.calculatorweb.entity.User;
import br.dev.ferreiras.calculatorweb.repository.OperationsRepository;
import br.dev.ferreiras.calculatorweb.repository.RoleRepository;
import br.dev.ferreiras.calculatorweb.repository.UserRepository;
import br.dev.ferreiras.calculatorweb.service.exceptions.ResourceNotFoundException;
import br.dev.ferreiras.calculatorweb.service.exceptions.UserAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;


@Service
public class UserService implements IUserService, UserDetailsService {

  public UserService(UserRepository userRepository, RoleRepository roleRepository, JwtEncoder jwtEncoder, OperationsRepository operationsRepository, final BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.jwtEncoder = jwtEncoder;
    this.operationsRepository = operationsRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  private final JwtEncoder jwtEncoder;

  private final OperationsRepository operationsRepository;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  @Override
  public User getUserId(UUID userId) {
    return this.userRepository.findById(userId).orElseThrow(
        () -> new ResourceNotFoundException("Resource not found!"));
  }

//  @Override
//  public User saveUser(final User user) {
//
//    return this.userRepository.save(user);
//  }

  @Override
  public List<User> findAllUsers() {

    return this.userRepository.findAll();
  }

  /**
   * @param username to be checked
   * @return User object if username exists
   */

  @Override
  public Optional<User> getUsername(String username) {

    return userRepository.findByUsername(username);
  }

  @Override
  public Role getRole() {

    return roleRepository.findByRole(Role.Roles.ROLE_USER.name());
  }

  public int updateBalance(String username, BigDecimal balance) {

    return userRepository.saveBalance(username, balance);
  }

  @Override
  public BigDecimal getBalance(String username) {

    return userRepository.findByUsernameBalance(username);
  }

  @Override
  public BigDecimal getOperationCostById(Long operationId) {

    return operationsRepository.findOperationsCostById(operationId);
  }

  @Override
  public LoadBalanceResponseDto addNewUser(final UserResponseDto userDto) {
    final User user = new User();
    if (this.getUsername(userDto.username()).isPresent()) {
      throw new UserAlreadyExistsException("User already exists!");
    } else {
      UserService.logger.info("::: Adding a new user! :::");

      final Role userRole = this.getRole();

      user.setUsername(userDto.username());
      user.setPassword(this.bCryptPasswordEncoder.encode(userDto.password()));
      user.setBalance(userDto.balance());
      user.setStatus(userDto.status());
      user.setRoles(Set.of(userRole));
      user.setCreatedBy("ricardo@ferreiras.dev.br");
      user.setCreatedDate(Instant.now());
      user.setLastModifiedBy("ricardo@ferreiras.dev.br");
      user.setLastModifiedDate(Instant.now());
      UserService.logger.info("::: User: {} :::", user);

      User result = new User();

      try {
        UserService.logger.info("::: Try-Catch code: :::");
        result = this.userRepository.save(user);
        UserService.logger.info("::: Result: {}", result);

      } catch(RuntimeException ex) {
        throw new IllegalArgumentException("Entity can not be null!");
      }

      return new LoadBalanceResponseDto(result.getUsername(), result.getBalance());
    }
  }

  /**
   * @param userRequestDto ( username, status )
   * @return status ACTIVE or INACTIVE
   */
  @Override
  public UserResponseDto activateUser(UserRequestDto userRequestDto) {

    String update = "";
    if (userRequestDto.username().isEmpty()) {
      throw new UsernameNotFoundException("Username not found!");
    } else {

      update = userRepository.updateStatus(userRequestDto.username(), userRequestDto.status());

    }

    return new UserResponseDto(userRequestDto.username(), update);
  }

  public BigDecimal getOperationCostByOperation(String operation) {

    return this.operationsRepository.findOperationsCostByOperation(operation);
  }

  public Optional<String> getLoggedUsername() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    final Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
    final String userAuthenticated = jwtPrincipal.getClaim("username");

    return userAuthenticated.describeConstable();
  }

//  public String authenticated() throws RuntimeException {
//
//
//    try {
//      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//      final Optional<User> user = userRepository.findById(UUID.fromString(authentication.getName()));
//      String currentUserName = user.get().getUsername().isEmpty() ? "ricardo@ferreiras.dev.br" : user.get().getUsername();
//      logger.info("CurrentUsername -> {}", currentUserName);
//      return "ricardo@ferreiras.dev.br";
//    } catch (RuntimeException e) {
//      throw new NoSuchElementException(e);
//    }
//  }

//    @Transactional (readOnly = true)
//  public UserDto getMe() {
//    Optional<User> entity = authenticated();
//    return new UserDto(entity);
//  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByUsername(username);
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

