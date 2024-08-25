package br.dev.ferreiras.calculatorWeb.service;

import br.dev.ferreiras.calculatorWeb.entity.Role;
import br.dev.ferreiras.calculatorWeb.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {
  User getUserId(UUID id);
  void saveUser(User user);
  List<User> findAllUsers();
  Optional<User> getUsername(String username);
  Role getRole();
  int updateBalance(String username, BigDecimal balance);
}
