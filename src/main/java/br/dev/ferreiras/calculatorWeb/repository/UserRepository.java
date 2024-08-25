package br.dev.ferreiras.calculatorWeb.repository;

import br.dev.ferreiras.calculatorWeb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByUsername(String username);

  @Transactional
  @Modifying
  @Query ("""
          UPDATE User u SET u.balance = :balance WHERE u.username = :username
          """
  )
  int saveBalance(String username, BigDecimal balance);
}
