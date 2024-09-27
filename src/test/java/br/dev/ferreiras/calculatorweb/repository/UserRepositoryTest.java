package br.dev.ferreiras.calculatorweb.repository;

import br.dev.ferreiras.calculatorweb.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {
  @Autowired
  private UserRepository userRepository;

  @Test
   void createUser() {

    Optional<User> user = this.userRepository.findByUsername("ricardo@ferreiras.dev.br");
    assertThat(user.orElseThrow().getUsername()).isEqualTo("ricardo@ferreiras.dev.br");
  }
}
