package br.dev.ferreiras.calculatorweb.service;

import br.dev.ferreiras.calculatorweb.dto.LoadBalanceResponseDto;
import br.dev.ferreiras.calculatorweb.dto.UserDto;
import br.dev.ferreiras.calculatorweb.dto.UserResponseDto;
import br.dev.ferreiras.calculatorweb.entity.User;
import br.dev.ferreiras.calculatorweb.repository.OperationsRepository;
import br.dev.ferreiras.calculatorweb.repository.RoleRepository;
import br.dev.ferreiras.calculatorweb.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@SpringBootTest
class UserServiceTest {
  private static final UUID ID = UUID.fromString("5259c2f6-fdcb-41ae-812f-bfa45cdd7c98");
  private static final String USERNAME  = "admin@calculatorweb.com";
  private static final String PASSWORD = "@c4lc5l4t0r@";
  private static final String STATUS = "ACTIVE";
  private static final BigDecimal Balance = BigDecimal.valueOf(100);
  private static final Set<String> roles = Set.of("ROLE_ADMIN");

  private User user;
  private UserDto userDto;
  private Optional<User> optionalUser;


  @InjectMocks
  private UserService userService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private RoleRepository roleRepository;

  @Mock
  private JwtEncoder jwtEncoder;

  @Mock
  private OperationsRepository operationsRepository;

  @Mock
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Mock
  private UserResponseDto userResponseDto;

  @Mock
  private LoadBalanceResponseDto loadBalanceResponseDto;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    this.startUser();
  }

  @Test
  void whenFindByIdThenReturnUserInstance() {
    Mockito.when(this.userRepository.findById((UserServiceTest.ID))).thenReturn(this.optionalUser);
    final User response = this.userService.getUserId(UserServiceTest.ID);

    Assertions.assertEquals(User.class, response.getClass());
  }

  @Test
  void saveUser() {
  }

  @Test
  void findAllUsers() {
  }

  @Test
  void whenGetUsernameThenReturnUserInstance() {
    Mockito.when(this.userRepository.findByUsername(UserServiceTest.USERNAME)).thenReturn(this.optionalUser);
    final Optional<User> response = this.userService.getUsername(UserServiceTest.USERNAME);
    Assertions.assertEquals(User.class, response.getClass());
  }

  @Test
  void getRole() {
  }

  @Test
  void updateBalance() {
  }

  @Test
  void getBalance() {
  }

  @Test
  void getOperationCostById() {
  }

  @Test
  void addNewUser() {
  }

  @Test
  void getOperationCostByOperation() {
  }

  @Test
  void authenticated() {
  }

  @Test
  void loadUserByUsername() {
  }

  private void startUser() {

    this.user = new User(ID, USERNAME, PASSWORD, STATUS, Balance);
    this.userDto = new UserDto(Optional.of(new User(ID, USERNAME, PASSWORD, STATUS, Balance )));
    this.optionalUser = Optional.of(new User(ID, USERNAME, PASSWORD, STATUS, Balance));

  }
}