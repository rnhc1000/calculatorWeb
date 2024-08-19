package br.dev.ferreiras.calculatorWeb.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name="tb_operation")
public class Operation implements Serializable {

  private static final long serialVersionUUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private BigDecimal cost;

//  @ElementCollection (targetClass = Operations.class)
//  @Enumerated(EnumType.STRING)
//  private  Operations operations;

}
