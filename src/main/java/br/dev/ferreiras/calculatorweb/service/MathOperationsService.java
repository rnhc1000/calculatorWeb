package br.dev.ferreiras.calculatorweb.service;

import br.dev.ferreiras.calculatorweb.dto.OperationsResponseDto;
import br.dev.ferreiras.calculatorweb.repository.OperationsRepository;
import br.dev.ferreiras.calculatorweb.service.exceptions.InvalidMathRequestException;
import br.dev.ferreiras.calculatorweb.service.exceptions.OutOfBalanceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


/**
 * This service class deal on how to make each operation, math or random string
 *
 * @author ricardo@ferreiras.dev.br
 * @version 1.1.030901
 * @since 08/2024
 */

@Service
public class MathOperationsService {

    private final UserService userService;
    private final RecordsService recordsService;
    private final OperationsRepository operationsRepository;
    private static final Logger logger = LoggerFactory.getLogger(MathOperationsService.class);
    private static final String exceptionOutOfBalance = "Insufficient funds to do maths! Reload your wallet!";
    final BigDecimal negativeBalance = new BigDecimal("8000863390488707.59991366095112916");

    public MathOperationsService(
            final UserService userService, final RecordsService recordsService,
            final OperationsRepository operationsRepository) {
        this.userService = userService;
        this.recordsService = recordsService;
        this.operationsRepository = operationsRepository;
    }

    private static final boolean deleted = false;

     /**
     * @param operandOne first operand
     * @param operandTwo second operand
     * @param operator   operator
     * @param username   username
     * @return string result
     */
    public OperationsResponseDto executeOperations(
            BigDecimal operandOne, BigDecimal operandTwo,
            final String operator, final String username) {

        if (null == operator) new OperationsResponseDto(this.negativeBalance, BigDecimal.ZERO);

        BigDecimal result = new BigDecimal("0");

        final var user = this.userService.getUsername(username);
        MathOperationsService.logger.info("usuario: {}", user.orElseThrow().getUsername());

        var balance = this.userService.getBalance(username);
        MathOperationsService.logger.info("balance: {}", balance);

        final var cost = this.userService.getOperationCostByOperation(operator);
        MathOperationsService.logger.info("cost: {}", cost);

        switch (operator) {

            case "addition" -> {

                if (0 <= balance.compareTo(cost)) {

                    operandOne = (null == operandOne ? BigDecimal.ZERO : operandOne);
                    operandTwo = (null == operandTwo ? BigDecimal.ZERO : operandTwo);
                    balance = balance.subtract(cost);
                    this.userService.updateBalance(username, balance);
                    final Addition addition = new Addition();
                    result = addition.mathOperations(operandOne, operandTwo);
                    this.recordsService.saveRecordsRandom(username, operandOne, operandTwo, operator, result, cost, MathOperationsService.deleted);

                } else {

                    throw new OutOfBalanceException(MathOperationsService.exceptionOutOfBalance);
                }
            }

            case "subtraction" -> {

                if (0 < balance.compareTo(cost)) {
                    operandOne = (null == operandOne ? BigDecimal.ZERO : operandOne);
                    operandTwo = (null == operandTwo ? BigDecimal.ZERO : operandTwo);
                    balance = balance.subtract(cost);
                    this.userService.updateBalance(username, balance);
                    final Subtraction subtraction = new Subtraction();
                    result = subtraction.operation(operandOne, operandTwo);
                    this.recordsService.saveRecordsRandom(username, operandOne, operandTwo, operator, result, cost, MathOperationsService.deleted);

                } else {

                    throw new OutOfBalanceException(MathOperationsService.exceptionOutOfBalance);
                }
            }

            case "multiplication" -> {

                if (0 < balance.compareTo(cost)) {

                    operandOne = (null == operandOne ? BigDecimal.ZERO : operandOne);
                    operandTwo = (null == operandTwo ? BigDecimal.ZERO : operandTwo);
                    balance = balance.subtract(cost);
                    this.userService.updateBalance(username, balance);
                    final Multiplication multiplication = new Multiplication();
                    result = multiplication.operation(operandOne, operandTwo);
                    this.recordsService.saveRecordsRandom(username, operandOne, operandTwo, operator, result, cost, MathOperationsService.deleted);

                } else {
                    throw new OutOfBalanceException(MathOperationsService.exceptionOutOfBalance);
                }
            }

            case "division" -> {
                operandOne = (null == operandOne ? BigDecimal.ZERO : operandOne);
                operandTwo = (null == operandTwo ? BigDecimal.ZERO : operandTwo);
                if (0 == operandTwo.compareTo(BigDecimal.ZERO)) {
                    MathOperationsService.logger.info("Comparison ok-> Negative Number, {}", operandTwo);
                    throw new InvalidMathRequestException("Illegal math operation!");
                }
                if (0 <= balance.compareTo(cost)) {
                    balance = balance.subtract(cost);
                    this.userService.updateBalance(username, balance);
                    final Division division = new Division();
                    result = division.operation(operandOne, operandTwo);
                    this.recordsService.saveRecordsRandom(username, operandOne, operandTwo, operator, result, cost, MathOperationsService.deleted);

                } else {

                    MathOperationsService.logger.info("Result -> Negative Number, {}", operandTwo);
                    throw new OutOfBalanceException(MathOperationsService.exceptionOutOfBalance);

                }
            }

            case "square_root" -> {
                operandOne = (null == operandOne ? BigDecimal.ZERO : operandOne);
                if (0 > operandOne.compareTo(BigDecimal.ZERO)) {

                    throw new InvalidMathRequestException("Illegal math operation!");
                }

                if (0 <= balance.compareTo(cost)) {
                    balance = balance.subtract(cost);
                    this.userService.updateBalance(username, balance);
                    final SquareRoot squareRoot = new SquareRoot();

                    result = squareRoot.operation(operandOne);
                    this.recordsService.saveRecordsRandom(username, operandOne, operandTwo, operator, result, cost, MathOperationsService.deleted);

                } else {

                    throw new OutOfBalanceException(MathOperationsService.exceptionOutOfBalance);

                }
            }

            case null -> {

                break;

            }

            default -> result = this.negativeBalance;

        }

        return new OperationsResponseDto(result, balance);
    }


    /**
     * @return list of operators implemented
     */
    public List<Object> getOperationsCost() {
        return this.operationsRepository.findAllCosts();
    }


}

