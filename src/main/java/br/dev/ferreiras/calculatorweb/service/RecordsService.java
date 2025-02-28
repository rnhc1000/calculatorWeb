package br.dev.ferreiras.calculatorweb.service;

import br.dev.ferreiras.calculatorweb.dto.RecordItemsDto;
import br.dev.ferreiras.calculatorweb.dto.RecordsDto;
import br.dev.ferreiras.calculatorweb.entity.Records;
import br.dev.ferreiras.calculatorweb.repository.RecordsRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RecordsService {

  private final RecordsRepository recordsRepository;

  private final EntityManager entityManager;

  public RecordsService(final RecordsRepository recordsRepository, final EntityManager entityManager) {
    this.recordsRepository = recordsRepository;

      this.entityManager = entityManager;
  }

  @Transactional
  public void saveRecordsRandom(
          final String username, final String operator, final String result, final BigDecimal cost
  ) {
    final Records records = new Records();
    records.setUsername(username);
    records.setOperator(operator);
    records.setResult(result);
    records.setCost(cost);

      this.recordsRepository.save(records);
  }

  @Transactional
  public void saveRecordsRandom(
          final String username, final String operator, final BigDecimal result, final BigDecimal cost
  ) {
    final Records records = new Records();
    records.setUsername(username);
    records.setOperator(operator);
    records.setResult(String.valueOf((result)));
    records.setCost(cost);

      this.recordsRepository.save(records);
  }

  @Transactional
  public void saveRecordsRandom(
          final String username, final BigDecimal operandOne, final BigDecimal operandTwo,
          final String operator, final BigDecimal result, final BigDecimal cost, final boolean deleted ) {

    final Records records = new Records();

    records.setUsername(username);
    records.setOperandOne(operandOne);
    records.setOperandTwo(operandTwo);
    records.setOperator(operator);
    records.setResult(String.valueOf((result)));
    records.setCost(cost);
    records.setDeleted(deleted);

    this.recordsRepository.save(records);
  }

  @Transactional
  public ResponseEntity<RecordsDto> getPagedRecords(final int page, final int size) {

    final Pageable paging = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

    final var pageRecords = this.recordsRepository.findAll(paging).map(records -> new RecordItemsDto(
            records.getRecordId(), records.getUsername(), records.getOperandOne(),
            records.getOperandTwo(), records.getOperator(), records.getResult(),
            records.getCost(), records.getCreatedAt(), records.getDeleted())
    );
    return ResponseEntity
            .ok(new RecordsDto
                    (pageRecords.getContent(), page, size,
                            pageRecords.getTotalPages(),
                            pageRecords.getTotalElements(), true
            )
    );

  }

  public ResponseEntity<RecordsDto> findRecordsByUsername(final int page, final int size, final String username) {

    final Pageable paging = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

    final var pageRecords = this.recordsRepository.findRecordsByUsername(username, paging).map(records -> new RecordItemsDto(
            records.getRecordId(), records.getUsername(), records.getOperandOne(),
            records.getOperandTwo(), records.getOperator(), records.getResult(),
            records.getCost(), records.getCreatedAt(), records.getDeleted())
    );
    return ResponseEntity
            .ok(new RecordsDto
                    (pageRecords.getContent(), page, size, pageRecords.getTotalPages(), pageRecords.getTotalElements(), true
                    )
            );
    //.cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)
  }

  public List<RecordsDto> findRecordsByUsernameStatus(final int page, final int size, final String username) {

    final Pageable paging = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
    final Session session = this.entityManager.unwrap(Session.class);
    final Filter filter = session.enableFilter("deletedTb_recordsFilter");
    filter.setParameter("isDeleted", false);

    final var pageRecords = this.recordsRepository.findRecordsByUsername(username, paging).map(records -> new RecordItemsDto(
            records.getRecordId(), records.getUsername(), records.getOperandOne(),
            records.getOperandTwo(), records.getOperator(), records.getResult(),
            records.getCost(), records.getCreatedAt(),records.getDeleted())
    );
    session.disableFilter("deletedTb_recordsFilter");
    return List.of(new RecordsDto
            (pageRecords.getContent(), page, size, pageRecords.getTotalPages(), pageRecords.getTotalElements(), true
            )
            );
    //.cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)
  }

  public void deleteRecordById(final long id) {

    this.recordsRepository.deleteById(id);
  }

  public List<RecordsDto> findSoftDeletedRecords(final int page, final int size, final String username) {

    final Pageable paging = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
    final Session session = this.entityManager.unwrap(Session.class);
    final Filter filter = session.enableFilter("deletedTb_recordsFilter");
    filter.setParameter("isDeleted", true);

    final var pageRecords = this.recordsRepository.findSoftDeletedRecordsByUsername(username, paging).map(records -> new RecordItemsDto(
            records.getRecordId(), records.getUsername(), records.getOperandOne(),
            records.getOperandTwo(), records.getOperator(), records.getResult(),
            records.getCost(), records.getCreatedAt(),records.getDeleted())
    );
    session.disableFilter("deletedTb_recordsFilter");
    return List.of(new RecordsDto
            (pageRecords.getContent(), page, size, pageRecords.getTotalPages(), pageRecords.getTotalElements(), true
            )
    );
    //.cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)
  }
}
