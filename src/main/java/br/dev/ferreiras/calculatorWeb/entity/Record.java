package br.dev.ferreiras.calculatorWeb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Entity
@Table (name = "tb_records")
public class Record implements Serializable {


  private static final long serialVersionUUID = 1L;

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long recordId;

  @NotNull
  private BigDecimal amount;

  private BigDecimal balance;

  private String operationResponse;

  @JoinColumn (name = "operation_id")
  @ManyToOne (cascade = CascadeType.ALL)
  private Operation operationId;

  @CreationTimestamp
  private Instant createdAt;

  @ManyToOne (cascade = CascadeType.ALL)
  @JoinColumn (name = "user_id")
  private User userId;

  public Record(Long recordId, BigDecimal amount, BigDecimal balance,
                String operationResponse, Operation operationId, Instant createdAt,
                User userId) {
    this.recordId = recordId;
    this.amount = amount;
    this.balance = balance;
    this.operationResponse = operationResponse;
    this.operationId = operationId;
    this.createdAt = createdAt;
    this.userId = userId;
  }

  public Record() {
  }

}
