package br.dev.ferreiras.calculatorweb.contracts;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity {

  @CreatedBy
  @Column(updatable = false, nullable = false)
  private String createdBy;

  @CreatedDate
  @Column(updatable = false, nullable = true)
  private Instant createdDate;

  @LastModifiedBy
  private String lastModifiedBy;

  @LastModifiedDate
  private Instant lastModifiedDate;

  public String getCreatedBy() {
    return this.createdBy;
  }

  public void setCreatedBy(final String createdBy) {
    this.createdBy = createdBy;
  }

  public Instant getCreatedDate() {
    return this.createdDate;
  }

  public void setCreatedDate(final Instant createdDate) {
    this.createdDate = createdDate;
  }

  public String getLastModifiedBy() {
    return this.lastModifiedBy;
  }

  public void setLastModifiedBy(final String lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  public Instant getLastModifiedDate() {
    return this.lastModifiedDate;
  }

  public void setLastModifiedDate(final Instant lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }
}
