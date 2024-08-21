package br.dev.ferreiras.calculatorWeb.entity;

import br.dev.ferreiras.calculatorWeb.dto.LoginRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_users")
public class User {
  private static final long serialVersionUUID = 1L;

  @Id
  @GeneratedValue (strategy = GenerationType.UUID)
  @Column(name = "user_id")
  private UUID userId;

  @NotBlank
  @Email
  @Size (min = 5, max = 40)
  @Column (unique = true)
  private String username;

  @Column (nullable = false)
  @NotBlank
  @Size (min = 8, max = 40)
  private String password;

  @Column (nullable = false)
  private String status;

  @CreationTimestamp
  private Instant createdAt;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable (
          name = "tb_users_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles;

  public UUID getUserId() {

    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public @NotBlank @Email @Size (min = 5, max = 40) String getUsername() {

    return username;
  }

  public void setUsername(@NotBlank @Email @Size (min = 5, max = 40) String username) {
    this.username = username;
  }

  public @NotBlank @Size (min = 8, max = 40) String getPassword() {

    return password;
  }

  public void setPassword(@NotBlank @Size (min = 8, max = 40) String password) {
    this.password = password;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public boolean isLoginCorrect(LoginRequestDto loginRequestDto, PasswordEncoder passwordEncoder) {

   return passwordEncoder.matches(loginRequestDto.password(), this.password);
  }
}
