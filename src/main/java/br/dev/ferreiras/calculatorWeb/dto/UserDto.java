package br.dev.ferreiras.calculatorWeb.dto;

import br.dev.ferreiras.calculatorWeb.entity.User;
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

  public UserDto(Optional<User> entity) {

    id = entity.get().getUserId();
    username = entity.get().getUsername();
    status = entity.get().getStatus();
    for (GrantedAuthority role : entity.get().getAuthorities()) {
      roles.add(role.getAuthority());
    }
  }

}
