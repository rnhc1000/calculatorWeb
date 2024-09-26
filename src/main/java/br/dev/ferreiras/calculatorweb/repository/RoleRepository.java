package br.dev.ferreiras.calculatorweb.repository;

import br.dev.ferreiras.calculatorweb.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  Role findByRole(String name);
}


