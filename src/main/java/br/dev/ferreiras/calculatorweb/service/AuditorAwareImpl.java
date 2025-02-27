package br.dev.ferreiras.calculatorweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

  private UserService userService;

  public AuditorAwareImpl() {
  }

  @Autowired
  public AuditorAwareImpl(final UserService userService) {
    this.userService = userService;
  }

  /**
   * @return
   */
  @Override
  @NonNull
  public Optional<String> getCurrentAuditor() {
    return Optional.of(this.userService.authenticated()).orElseGet(
        () -> "ricardo@ferreira.dev.br").describeConstable();

//    return "ricardo@ferreiras.dev.br".describeConstable();
    }
}
