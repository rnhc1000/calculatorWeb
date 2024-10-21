package br.dev.ferreiras.calculatorweb.config;

import br.dev.ferreiras.calculatorweb.entity.User;
import br.dev.ferreiras.calculatorweb.repository.RoleRepository;
import br.dev.ferreiras.calculatorweb.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@SpringBootTest

class AdminUserConfigurationTest {

  @InjectMocks
  private AdminUserConfiguration adminUserConfiguration;

  @Mock
  private  RoleRepository roleRepository;

  @Mock
  private  UserRepository userRepository;

  @Mock
  private  BCryptPasswordEncoder bCryptPasswordEncoder;

  private User user;

  @BeforeEach
  void setUp() {
  }

  @Test
  void run() {

    Mockito.when(this.userRepository.findByUsername(("admin@calculatorweb.com"))).thenReturn(Optional.ofNullable(this.user));
    final Optional<User> user = this.userRepository.findByUsername("admin@calculatorweb.com");
    Assertions.assertEquals("admin@calculatorweb.com", (user.get().getUsername()));

  }
}