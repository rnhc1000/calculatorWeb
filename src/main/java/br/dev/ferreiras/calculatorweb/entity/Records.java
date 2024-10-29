package br.dev.ferreiras.calculatorweb.entity;

import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE tb_records SET deleted=true where record_id = ?")
@FilterDef(
        name = "deletedTb_recordsFilter",
        parameters = @ParamDef(name = "isDeleted", type = org.hibernate.type.descriptor.java.BooleanJavaType.class)
)
@Filter(
        name = "deletedTb_recordsFilter",
        condition = "deleted = :isDeleted"
)
@Table (name = "tb_records")
public class Records implements Serializable {

  private static final long serialVersionUUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long recordId;

  private String username;

  private BigDecimal operandOne;

  private BigDecimal operandTwo;

  private String operator;

  private String result;

  private boolean deleted = Boolean.FALSE;

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

  public boolean getDeleted() {
    return this.deleted;
  }
}
