package br.dev.ferreiras.calculatorWeb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class User {
  private static final long serialVersionUUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long userId;

  @Column (nullable = false)
  @NotBlank
  @Email
  @Size (min = 5, max = 40)
  private String username;

  @Column (nullable = false)
  @NotBlank
  @Size (min = 8, max = 40)
  private String password;

  @Column (nullable = false)
  private String status;

}
