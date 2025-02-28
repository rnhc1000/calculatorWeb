package br.dev.ferreiras.calculatorweb.repository;

import br.dev.ferreiras.calculatorweb.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigDecimal;
import java.util.List;

@RepositoryRestResource
public interface OperationsRepository extends JpaRepository<Operation, Long> {

  @Query ("""
          SELECT cost c FROM Operation o WHERE operationId = :operationId
          """)
  BigDecimal findOperationsCostById(Long operationId);

  @Query ("""
          SELECT cost c FROM Operation o WHERE operation = :operation
          """)
  BigDecimal findOperationsCostByOperation(String operation);

  @Query ("""
          SELECT o.operation,  o.cost c FROM Operation o
          """)
  List<Object> findAllCosts();

}
