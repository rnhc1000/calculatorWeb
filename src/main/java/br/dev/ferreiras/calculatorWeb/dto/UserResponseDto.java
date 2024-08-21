package br.dev.ferreiras.calculatorWeb.dto;

import br.dev.ferreiras.calculatorWeb.entity.User;

import java.time.Instant;
import java.util.UUID;

public class UserResponseDto {
  private final UUID id;
  private final String username;
  private final String status;
  private final Instant createdAt;

  public UserResponseDto(UUID id, String username, String status, Instant createdAt) {
    this.id = id;
    this.username = username;
    this.status = status;
    this.createdAt = createdAt;
  }

  public UserResponseDto(User entity) {
    id = entity.getUserId();
    username = entity.getUsername();
    status = entity.getStatus();
    createdAt = entity.getCreatedAt();
  }

  public UUID getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getStatus() {
    return status;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }
}
