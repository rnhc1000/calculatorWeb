package br.dev.ferreiras.calculatorWeb.entity;

import br.dev.ferreiras.calculatorWeb.dto.LoginRequestDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table (name = "tb_users")
public class User implements UserDetails {
  private static final long serialVersionUUID = 1L;

  @Id
  @GeneratedValue (strategy = GenerationType.UUID)
  private UUID userId;

  @NotBlank
  @Email
  @Size (min = 5, max = 40)
  @Column (unique = true)
  private String username;

  @Column (nullable = false)
  @NotBlank
  @Size (min = 10, max = 100)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @Column (nullable = false)
  private String status;

  private BigDecimal balance;

  @CreationTimestamp
  private Instant createdAt;

  @ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable (
          name = "tb_users_roles",
          joinColumns = @JoinColumn (name = "user_id"),
          inverseJoinColumns = @JoinColumn (name = "role_id")
  )
  private Set<Role> roles;

  public User(UUID userId, Set<Role> roles, String username, String password,
              String status, BigDecimal balance, Instant createdAt) {
    this.userId = userId;
    this.roles = roles;
    this.username = username;
    this.password = password;
    this.status = status;
    this.balance = balance;
    this.createdAt = createdAt;
  }

  @Column(nullable = false, precision = 2)
  public BigDecimal getBalance() {

    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public User() {
  }

  public UUID getUserId() {

    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public @NotBlank @Email @Size (min = 5, max = 40) String getUsername() {

    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return UserDetails.super.isEnabled();
  }

  public void setUsername(@NotBlank @Email @Size (min = 5, max = 40) String username) {
    this.username = username;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }

  public @NotBlank @Size (min = 10, max = 100) String getPassword() {

    return password;
  }

  public void setPassword(@NotBlank @Size (min = 10, max = 100) String password) {
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
