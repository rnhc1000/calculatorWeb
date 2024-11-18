package br.dev.ferreiras.calculatorweb.dto;

import br.dev.ferreiras.calculatorweb.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class UserDto {

  private UUID id;
  private final String username;
  private final String status;
  private final Set<String> roles = new HashSet<>();

  public UserDto(final UUID id, final String username, final String status) {

    this.id = id;
    this.username = username;
    this.status = status;

  }

  public UserDto(final User entity) {

    this.id = entity.getUserId();
    this.username = entity.getUsername();
    this.status = entity.getStatus();
    for (final GrantedAuthority role : entity.getAuthorities()) {
      this.roles.add(role.getAuthority());
    }

  }

  public UserDto(final String username, final String status) {
    this.username = username;
    this.status = status;
  }
}
