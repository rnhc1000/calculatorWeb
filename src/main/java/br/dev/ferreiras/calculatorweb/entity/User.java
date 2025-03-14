package br.dev.ferreiras.calculatorweb.entity;

import br.dev.ferreiras.calculatorweb.contracts.AuditableEntity;
import br.dev.ferreiras.calculatorweb.dto.LoginRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@ClassPreamble(
    author = "Ricardo Ferreira",
    date = "2025-01-01",
    currentRevision = 4,
    lastModified = "2025-02-28",
    reviewers = {"Ricardo Ferreira", "757 Team"},
    description = "User Entity"
)

@Entity
@Table(name = "tb_users")
public class User extends AuditableEntity implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID userId;

  @NotBlank
  @Email
  @Size(min = 5, max = 40)
  @Column(unique = true)
  @NotNull
  private String username;

  private String password;

  @Column(nullable = false)
  private String status;

  private BigDecimal balance;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(
      name = "tb_users_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles;

  public User(UUID userId, String username, String password, String status, BigDecimal balance) {
    this.userId = userId;
    this.username = username;
    this.password = password;
    this.status = status;
    this.balance = balance;
  }

  public User(@NotBlank @Email @Size(min = 5, max = 40) String username) {
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

  public @NotBlank @Email @Size(min = 5, max = 40) String getUsername() {

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

  public void setUsername(@NotBlank @Email @Size(min = 5, max = 40) String username) {
    this.username = username;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }

  public @NotBlank @Size(min = 10, max = 100) String getPassword() {

    return password;
  }

  public void setPassword(@NotBlank @Size(min = 10, max = 100) String password) {

    this.password = password;
  }

  public String getStatus() {

    return status;
  }

  public void setStatus(String status) {

    this.status = status;
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

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof User user)) return false;
    return Objects.equals(userId, user.userId)
           && Objects.equals(username, user.username)
           && Objects.equals(password, user.password)
           && Objects.equals(status, user.status)
           && (balance == null ? user.balance == null : balance.compareTo(user.balance) == 0)
          && Objects.equals(roles, user.roles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, username, password, status, balance, roles);
  }

  @Override
  public String toString() {
    return "User{" +
           "userId=" + userId +
           ", username='" + username + '\'' +
           ", password='" + password + '\'' +
           ", status='" + status + '\'' +
           ", balance=" + balance +
           ", roles=" + roles +
           '}';
  }
}
