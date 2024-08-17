package br.dev.ferreiras.calculatorWeb.repository;

import br.dev.ferreiras.calculatorWeb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Query (value = """
           select status from User u where u.username = :username
          """)
  boolean isActive(String username);
}
