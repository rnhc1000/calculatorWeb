package br.dev.ferreiras.calculatorWeb.entity;

import jakarta.persistence.*;

@Entity
public class User {
  private static final long serialVersionUUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long userId;

  @Column (nullable = false)
  private String username;

  @Column (nullable = false)
  private String password;

  @Column (nullable = false)
  private String status;

}
