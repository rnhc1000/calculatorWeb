/*
 * Copyright (c) 2025.
 * ricardo@ferreiras.dev.br
 */

package br.dev.ferreiras.calculatorweb.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BalanceService {

  private final UserService userService;

  public BalanceService(final UserService userService) {
    this.userService = userService;
  }

  public boolean balanceCheck(final String operator, final BigDecimal balance) {

    final BigDecimal costOfOperation = this.userService.getOperationCostByOperation(operator);

    final int result = balance.compareTo(costOfOperation);

    return switch (result) {

      case 0, 1:
        yield true;

      case -1:
        yield false;

      default:
        throw new IllegalStateException("Unexpected value: " + result);
    };
  }
}
