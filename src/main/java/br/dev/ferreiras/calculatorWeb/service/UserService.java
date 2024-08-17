package br.dev.ferreiras.calculatorWeb.service;

import br.dev.ferreiras.calculatorWeb.entity.User;
import br.dev.ferreiras.calculatorWeb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;


  @Transactional
  public boolean userExists(String username) {

    return userRepository.isActive(username);
  }

  @Transactional (readOnly = true)
  public UserResponseDto getUserById(Long userId) {

    User user = userRepository.findById(userId).orElseThrow(
            () -> new ResourceNotFoundException("Resource not found!"));

    return MapperMessages.INSTANCE.userEntityToDto(user);
  }

}
