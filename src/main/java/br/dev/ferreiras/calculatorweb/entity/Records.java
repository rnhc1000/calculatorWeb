package br.dev.ferreiras.calculatorweb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table (name = "tb_records")
public class Records implements Serializable {

  private static final long serialVersionUUID = 1L;

  @Id
  @GeneratedValue (strategy = GenerationType.SEQUENCE)
  private Long recordId;

  private String username;

  private BigDecimal operandOne;

  private BigDecimal operandTwo;

  private String operator;

  private String result;

  @NotNull
  private BigDecimal cost;

  @CreationTimestamp
  private Instant createdAt;

//  @ManyToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
//  @JoinColumn(name = "user_id")
//  private User user;

  @PrePersist
  public void prePersist() {
    if (operandOne == null) operandOne = new BigDecimal("0.000");
    if (operandTwo == null) operandTwo = new BigDecimal("0.000");
  }

  public Records() {
  }

  public Records(Long recordId, String username, BigDecimal operandOne,
                 BigDecimal operandTwo, String operator, String result,
                 BigDecimal cost) {

    this.recordId = recordId;
    this.username = username;
    this.operandOne = operandOne;
    this.operandTwo = operandTwo;
    this.operator = operator;
    this.result = result;
    this.cost = cost;

  }
}
