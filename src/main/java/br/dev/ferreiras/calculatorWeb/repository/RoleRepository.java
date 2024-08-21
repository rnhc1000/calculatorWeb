package br.dev.ferreiras.calculatorWeb.repository;

import br.dev.ferreiras.calculatorWeb.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
//  Role findByRole(String role);

  Role findByRole(String role);
//  @Query (value = """
//           select role from tb_roles t where t.role = :role
//          """)
//  Role getRole();
}


