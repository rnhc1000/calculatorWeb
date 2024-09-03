package br.dev.ferreiras.calculatorWeb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_roles")
public class Role {

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  @Column (name = "role_id")
  private Long roleId;

  @Column (name = "role")
  private String role;

  @Getter
  public enum Roles {
    ROLE_ADMIN(1L),
    ROLE_USER(2L);

    final long roleId;
    Roles(long roleId) {
      this.roleId = roleId;
    }

  }

}
