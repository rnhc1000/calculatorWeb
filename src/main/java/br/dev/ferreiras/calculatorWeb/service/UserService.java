package br.dev.ferreiras.calculatorWeb.service;

import br.dev.ferreiras.calculatorWeb.entity.Role;
import br.dev.ferreiras.calculatorWeb.entity.User;
import br.dev.ferreiras.calculatorWeb.repository.OperationsRepository;
import br.dev.ferreiras.calculatorWeb.repository.RoleRepository;
import br.dev.ferreiras.calculatorWeb.repository.UserRepository;
import br.dev.ferreiras.calculatorWeb.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService implements IUserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private JwtEncoder jwtEncoder;

  @Autowired
  private OperationsRepository operationsRepository;

  public UserService() {
  }

  public UserService(UserRepository userRepository, RoleRepository roleRepository, JwtEncoder jwtEncoder, OperationsRepository operationsRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.jwtEncoder = jwtEncoder;
    this.operationsRepository = operationsRepository;
  }

  @Override
  public User getUserId(UUID userId) {
    return userRepository.findById(userId).orElseThrow(
            () -> new ResourceNotFoundException("Resource not found!"));
  }

  @Override
  public void saveUser(User user) {
    userRepository.save(user);
  }

  @Override
  public List<User> findAllUsers() {

    return userRepository.findAll();
  }

  @Override
  public Optional<User> getUsername(String username) {

    return Optional.ofNullable(userRepository.findByUsername(username).orElseThrow(
            () -> new ResourceNotFoundException("Resource not found!")));
  }

  @Override
  public Role getRole() {

    return roleRepository.findByRole(Role.Roles.ROLE_USER.name());
  }

  @Override
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
