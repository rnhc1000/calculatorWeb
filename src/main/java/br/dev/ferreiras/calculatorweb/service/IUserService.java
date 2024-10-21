package br.dev.ferreiras.calculatorweb.service;

import br.dev.ferreiras.calculatorweb.dto.LoadBalanceResponseDto;
import br.dev.ferreiras.calculatorweb.dto.UserRequestDto;
import br.dev.ferreiras.calculatorweb.dto.UserResponseDto;
import br.dev.ferreiras.calculatorweb.entity.Role;
import br.dev.ferreiras.calculatorweb.entity.User;

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
  BigDecimal getBalance(String username);
  BigDecimal getOperationCostById(Long id);
  LoadBalanceResponseDto addNewUser(UserResponseDto dto);
  UserResponseDto activateUser(UserRequestDto userRequestDto);
}
