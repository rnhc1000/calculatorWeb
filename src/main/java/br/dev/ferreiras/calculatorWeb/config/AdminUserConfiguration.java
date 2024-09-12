package br.dev.ferreiras.calculatorWeb.config;

import br.dev.ferreiras.calculatorWeb.entity.Role;
import br.dev.ferreiras.calculatorWeb.entity.User;
import br.dev.ferreiras.calculatorWeb.repository.RoleRepository;
import br.dev.ferreiras.calculatorWeb.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

/** This class provides a user with administrator privileges with an initial balance of $1000.00
 * @author ricardo@ferreiras.dev.br
 * @version 1.1.030901
 * @since 08/2024
 */


@Configuration
public class AdminUserConfiguration implements CommandLineRunner {
  private final static Logger logger = LoggerFactory.getLogger(AdminUserConfiguration.class);

  private final RoleRepository roleRepository;

  private final UserRepository userRepository;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public AdminUserConfiguration(RoleRepository roleRepository,
                                UserRepository userRepository,
                                BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.roleRepository = roleRepository;
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  /**
   *
   * Override run method from CommandLineRunner interface to create a
   * user with Role:Admin if it already does not exist persisted into the database
   * @throws Exception Database Exception
   */

  @Override
  @Transactional
  public void run(String... args) throws Exception {

    Role roleAdmin = roleRepository.findByRole(Role.Roles.ROLE_ADMIN.name());

    logger.info("RoleAdmin:-> {}", roleAdmin);

    var userAdmin = userRepository.findByUsername("admin@calculatorweb.com");

    logger.info("UserAdmin:-> {}", userAdmin);

    userAdmin.ifPresentOrElse(
            user -> {
              logger.info("Administrator already exists!");
            },
            () -> {

              var user = new User();

              user.setUsername("admin@calculatorweb.com");
              user.setPassword(bCryptPasswordEncoder.encode("@c4lc5l4t0r@"));
              user.setRoles(Set.of(roleAdmin));
              user.setBalance(new BigDecimal("1000.00"));
              user.setStatus("ACTIVE");
              user.setCreatedAt(Instant.now());

              userRepository.save(user);
              logger.info("Administrator created");
            }
    );
  }
}
