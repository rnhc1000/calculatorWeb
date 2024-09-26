package br.dev.ferreiras.calculatorweb.dto;

import br.dev.ferreiras.calculatorweb.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Getter
public class UserDto {
  private UUID id;
  private final String username;
  private final String status;
  private final Set<String> roles = new HashSet<>();

  public UserDto(final Optional<User> entity) {

    this.id = entity.orElseThrow().getUserId();
    this.username = entity.orElseThrow().getUsername();
    this.status = entity.orElseThrow().getStatus();
    for (final GrantedAuthority role : entity.orElseThrow().getAuthorities()) {
      this.roles.add(role.getAuthority());
    }
  }

}
