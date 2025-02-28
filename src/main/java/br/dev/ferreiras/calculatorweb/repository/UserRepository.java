package br.dev.ferreiras.calculatorweb.repository;

import br.dev.ferreiras.calculatorweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;


@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByUsername(String username);

  @Transactional
  @Modifying
  @Query ("""
          UPDATE User u SET u.balance = :balance, u.lastModifiedDate = CURRENT_TIMESTAMP WHERE u.username = :username
          """
  )
  int saveBalance(String username, BigDecimal balance);

  @Query("""
          SELECT balance b from User u WHERE u.username = :username
          """)
  BigDecimal findByUsernameBalance(String username);

  @Transactional
  @Modifying
  @Query("""
          UPDATE User u SET u.status = :status, u.lastModifiedDate = CURRENT_TIMESTAMP WHERE u.username = :username
          """)
  String updateStatus(String username, String status);
}
