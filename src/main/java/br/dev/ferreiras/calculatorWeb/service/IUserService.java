package br.dev.ferreiras.calculatorWeb.service;

import br.dev.ferreiras.calculatorWeb.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface IUserService {
  User findById(UUID id);
  User save(User user);
  Iterable<User> findAll();
  Optional<User> getUsername(String username);
}
