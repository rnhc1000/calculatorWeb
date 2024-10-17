package br.dev.ferreiras.calculatorweb.service;

import br.dev.ferreiras.calculatorweb.dto.RecordItemsDto;
import br.dev.ferreiras.calculatorweb.dto.RecordsDto;
import br.dev.ferreiras.calculatorweb.entity.Records;
import br.dev.ferreiras.calculatorweb.repository.RecordsRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class RecordsService {

  private final RecordsRepository recordsRepository;
  private final UserService userService;

  public RecordsService(final RecordsRepository recordsRepository, final UserService userService) {
    this.recordsRepository = recordsRepository;
    this.userService = userService;
  }

  @Transactional
  public void saveRecordsRandom(
          String username, String operator, String result, BigDecimal cost
  ) {
    Records records = new Records();
    records.setUsername(username);
    records.setOperator(operator);
    records.setResult(result);
    records.setCost(cost);

    recordsRepository.save(records);
  }

  @Transactional
  public void saveRecordsRandom(
          String username, String operator, BigDecimal result, BigDecimal cost
  ) {
    Records records = new Records();
    records.setUsername(username);
    records.setOperator(operator);
    records.setResult(String.valueOf((result)));
    records.setCost(cost);

    recordsRepository.save(records);
  }

  @Transactional
  public void saveRecordsRandom(
          final String username, BigDecimal operandOne, BigDecimal operandTwo, String operator, BigDecimal result, BigDecimal cost
  ) {
    Records records = new Records();

    records.setUsername(username);
    records.setOperandOne(operandOne);
    records.setOperandTwo(operandTwo);
    records.setOperator(operator);
    records.setResult(String.valueOf((result)));
    records.setCost(cost);

    this.recordsRepository.save(records);
  }

  @Transactional
  public ResponseEntity<RecordsDto> getPagedRecords(int page, int size) {

    final Pageable paging = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

//      Page<Records> pageRecords;
    final var pageRecords = this.recordsRepository.findAll(paging).map(records -> new RecordItemsDto(
            records.getRecordId(), records.getUsername(), records.getOperandOne(),
            records.getOperandTwo(), records.getOperator(), records.getResult(),
            records.getCost(), records.getCreatedAt())
    );
    return ResponseEntity
            .ok(new RecordsDto
                    (pageRecords.getContent(), page, size, pageRecords.getTotalPages(), pageRecords.getTotalElements()
            )
    );

  }

  public ResponseEntity<RecordsDto> findRecordsByUsername(final int page, final int size, final String username) {

    Pageable paging = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

    var pageRecords = this.recordsRepository.findRecordsByUsername(username, paging).map(records -> new RecordItemsDto(
            records.getRecordId(), records.getUsername(), records.getOperandOne(),
            records.getOperandTwo(), records.getOperator(), records.getResult(),
            records.getCost(), records.getCreatedAt())
    );
    return ResponseEntity
            .ok(new RecordsDto
                    (pageRecords.getContent(), page, size, pageRecords.getTotalPages(), pageRecords.getTotalElements()
                    )
            );
    //.cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)
  }
}
